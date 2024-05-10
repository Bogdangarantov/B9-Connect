let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user:null,
        cookieValue:"",
        service:{},
        faq:[],
        newTicket :{
            name:"",
            problem:"",
            service_id:""
        }
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
        async sendTIcket(e){
            let link = new URLSearchParams(window.location.search)
            e.preventDefault()
            this.newTicket.service_id = link.get("service_id")
            console.log(this.newTicket)
            let response = await fetch('/api/v1/services/ticket', {
                method: 'POST',
                headers: {
                    'Content-type':"application/json",
                    'X-XSRF-TOKEN': this.cookieValue
                },
                body:JSON.stringify(this.newTicket)
            })
            if (response.ok) {

                await location.reload()
            }
        },
        async logout() {
            let response = await fetch('/logout', {
                method: 'POST',
                headers: {
                    'Content-type':"application/json",
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