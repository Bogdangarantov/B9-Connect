
let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user:null,
        cookieValue:"",
        websocket:{},
        chats:[],
        currentChat:{},
        stompClient :null
    },
    methods:{
        getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        },
        getUser(){
            let th = this
            let user = JSON.parse(document.getElementById("user").value)
            th.user = user
            user.value =""
        },
        async getChats(){
            let response = await fetch("/api/v1/services/ticket",{
                method:"GET",
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok){
                let data = JSON.parse(await response.text())
                this.chats = data
                this.chats.forEach(chat=> chat.active = false)
                console.log(this.chats)
            }
        },
        async setCurrentChat(chat,index){
            console.log("set")
            this.currentChat = chat
            this.chats.forEach(chat=> chat.active=false)
            this.chats[index].active = true
            await this.sleep(100)
            this.scrollDown()

        },
        async sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        },
        getActive(index){
            if (this.chats[index].active){
                return "active"
            }else{
                return ""
            }
        },
        scrollDown(){
            let chatHistory = document.getElementById("chat-history")
            chatHistory.scrollTo(0,document.body.scrollHeight)
        },
        async getChatHistory(){

        },
        async socket(){
            var socket = new SockJS('/chat');
            var stompClient = Stomp.over(socket);

            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function(messageOutput) {
                    showMessage(JSON.parse(messageOutput.body).content);
                });
            });

            function sendMessage(message) {
                stompClient.send("/app/sendMessage", {}, JSON.stringify({'content': message}));
            }

            function showMessage(message) {
                // Показати повідомлення в чаті
            }
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
        async sock(){
            this.stompClient = new StompJs.Client({
                brokerURL: 'ws://localhost:8082/chat'
            });

            this.stompClient.onConnect = (frame) => {
                this.setConnected(true);
                console.log('Connected: ' + frame);
                this.stompClient.subscribe('/topic/public', (greeting) => {
                    console.log(JSON.parse(greeting.body).content);
                });
            };

            this.stompClient.onWebSocketError = (error) => {
                console.error('Error with websocket', error);
            };

            this.stompClient.onStompError = (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            };
        },
        connect() {
            this.stompClient.activate();
        },
        disconnect() {
            this.stompClient.deactivate();
            this.setConnected(false);
            console.log("Disconnected");
        },
        sendMessage(message) {
            this.stompClient.publish({
                destination: "/chat",
                body: JSON.stringify(message)
            });
        }    },
    async created(){
        this.cookieValue = this.getCookie('XSRF-TOKEN')
        this.getUser()
        // await this.socket()
        await this.getChats()
        await this.sock()
        this.connect()
        this.sendMessage({
            ticket_id:6,
            message:"huj vam v ruki",

        })


    }
})