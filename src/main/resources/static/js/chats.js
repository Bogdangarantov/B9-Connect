let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user:null,
        cookieValue:""
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
            }
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
    },
    async created(){
        this.cookieValue = this.getCookie('XSRF-TOKEN')
        this.getUser()
    }
})