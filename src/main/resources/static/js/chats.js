let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user: null,
        cookieValue: "",
        websocket: {},
        chats: [],
        currentChat: {},
        stompClient: null,
        messages: [],
        socket: null,
        chatMessage: "",
        colors: [
            '#2196F3', '#32c787', '#00BCD4', '#ff5652',
            '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
        ]

    },
    methods: {
        getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        },
        getUser() {
            let th = this
            let user = JSON.parse(document.getElementById("user").value)
            th.user = user
            user.value = ""
        },
        async getChats() {
            let response = await fetch("/api/v1/services/ticket", {
                method: "GET",
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                let data = JSON.parse(await response.text())
                console.log(this.chats)
                data.forEach(chat => chat.active = false)
                this.chats = data
                console.log(this.chats)
            }
        },
        async setCurrentChat(chat, index) {
            console.log("set")
            this.currentChat = chat
            this.messages = this.sortDates(this.currentChat.ticketMessages)
            console.log(this.currentChat)
            this.chats.forEach(chat => chat.active = false)
            this.chats[index].active = true
            if (this.stompClient !== null) {
                await this.stompClient.disconnect()
                await this.connect(chat.id)


            } else {
                await this.connect(chat.id)
            }

            await this.sleep(100)
            this.scrollDown()

        },
        async sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        },
        formatDate(isoString) {
            let date = new Date(isoString);
            let day = date.getDate();
            let month = date.getMonth() + 1;
            let year = date.getFullYear();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();
            day = day < 10 ? '0' + day : day;
            month = month < 10 ? '0' + month : month;
            hours = hours < 10 ? '0' + hours : hours;
            minutes = minutes < 10 ? '0' + minutes : minutes;
            seconds = seconds < 10 ? '0' + seconds : seconds;
            return day + '-' + month + '-' + year + ' ' + hours + ':' + minutes + ':' + seconds;
        },
        getActive(index) {
            if (this.chats[index].active) {
                return "active"
            } else {
                return ""
            }
        },
        scrollDown() {
            let chatHistory = document.getElementById("chat-history")
            chatHistory.scrollTo(0, chatHistory.scrollHeight)
        },
        async getChatHistory() {

        },
        async logout() {
            let response = await fetch('/logout', {
                method: 'POST',
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                await location.reload()
            }
        },
        async connect() {
            this.socket = new SockJS('/ws');
            this.stompClient = Stomp.over(this.socket);
            this.stompClient.connect({}, this.onConnected, this.onError);
        },
        onConnected() {
            this.stompClient.subscribe('/topic/' + this.currentChat.id, this.onMessageReceived);
            this.stompClient.send("/app/chat/" + this.currentChat.id + "/addUser",
                {},
                JSON.stringify({
                    id: this.user.id,
                    name: this.user.name
                })
            )
        },
        sortDates(dates) {
            return dates.sort((a, b) => new Date(a.created) - new Date(b.created));
        },
        onError(error) {
            connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
            connectingElement.style.color = 'red';
        },
        async sendMessage(event) {
            event.preventDefault();
            if (this.chatMessage && this.stompClient) {
                let chatMessage = {
                    user_id: this.user.id,
                    message: this.chatMessage
                };
                this.stompClient.send("/app/chat/" + this.currentChat.id + "/sendMessage", {}, JSON.stringify(chatMessage));
                this.chatMessage = ""
            }
        },
        onMessageReceived(payload) {
            let message = JSON.parse(payload.body);
            this.messages.push(message)
            const sleep = milliseconds => new Promise(resolve => setTimeout(resolve, milliseconds));
            sleep(100).then(this.scrollDown)
        },
        getAvatarColor(messageSender) {
            let hash = 0;
            for (let i = 0; i < messageSender.length; i++) {
                hash = 31 * hash + messageSender.charCodeAt(i);
            }
            let index = Math.abs(hash % this.colors.length);
            console.log(this.colors[index])
            return this.colors[index];
        },
    },
    async created() {
        this.cookieValue = this.getCookie('XSRF-TOKEN')
        this.getUser()
        await this.getChats()
    }
})