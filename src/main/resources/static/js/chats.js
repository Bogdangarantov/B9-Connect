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
        chatMessage:""

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
                this.chats = JSON.parse(await response.text())
                this.chats.forEach(chat => chat.active = false)
                console.log(this.chats)
            }
        },
        async setCurrentChat(chat, index) {
            console.log("set")
            this.currentChat = chat
            this.chats.forEach(chat => chat.active = false)
            this.chats[index].active = true
            if (this.stompClient!==null){
                await this.stompClient.disconnect()
                await this.connect(chat.id)
            }else{
                await this.connect(chat.id)
            }

            await this.sleep(100)
            this.scrollDown()

        },
        async sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
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
            chatHistory.scrollTo(0, document.body.scrollHeight)
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
            // Subscribe to the Public Topic
            this.stompClient.subscribe('/topic/'+this.currentChat.id, this.onMessageReceived);

            // Tell your username to the server
            this.stompClient.send("/app/chat/"+this.currentChat.id+"/addUser",
                {},
                JSON.stringify({
                    id:this.user.id,
                    name:this.user.name
                })
            )
        },
        onError(error) {
            connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
            connectingElement.style.color = 'red';
        },
        async sendMessage(event) {
            if (this.chatMessage && this.stompClient) {
                let chatMessage = {
                    user_id:this.user.id,
                    message:this.chatMessage
                };
                this.stompClient.send("/app/chat/"+this.currentChat.id+"/sendMessage", {}, JSON.stringify(chatMessage));
                this.chatMessage = ""
            }
            event.preventDefault();
        },
        onMessageReceived(payload) {
            let message = JSON.parse(payload.body);
            console.log("NEW MESSAGE")
            console.log(message)
            this.messages.push(message)
            // var messageElement = document.createElement('li');
            //
            // if (message.type === 'JOIN') {
            //     messageElement.classList.add('event-message');
            //     message.content = message.sender + ' joined!';
            // } else if (message.type === 'LEAVE') {
            //     messageElement.classList.add('event-message');
            //     message.content = message.sender + ' left!';
            // } else {
            //     messageElement.classList.add('chat-message');
            //
            //     var avatarElement = document.createElement('i');
            //     var avatarText = document.createTextNode(message.sender[0]);
            //     avatarElement.appendChild(avatarText);
            //     avatarElement.style['background-color'] = getAvatarColor(message.sender);
            //
            //     messageElement.appendChild(avatarElement);
            //
            //     var usernameElement = document.createElement('span');
            //     var usernameText = document.createTextNode(message.sender);
            //     usernameElement.appendChild(usernameText);
            //     messageElement.appendChild(usernameElement);
            // }
            // var textElement = document.createElement('p');
            // var messageText = document.createTextNode(message.content);
            // textElement.appendChild(messageText);
            //
            // messageElement.appendChild(textElement);
            //
            // messageArea.appendChild(messageElement);
            // messageArea.scrollTop = messageArea.scrollHeight;
        },
        async getAvatarColor(messageSender) {
            let hash = 0;
            for (let i = 0; i < messageSender.length; i++) {
                hash = 31 * hash + messageSender.charCodeAt(i);
            }
            let index = Math.abs(hash % colors.length);
            return colors[index];
        },
    },
    async created() {
        this.cookieValue = this.getCookie('XSRF-TOKEN')
        this.getUser()
        await this.getChats()
    }
})