let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user:null,
        cookieValue:"",
        service:{},
        faq:[]
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
        async getServiceFaq(){
            let link = new URLSearchParams(window.location.search)
            let response = await fetch('/api/v1/services/faq/'+link.get("service_id"), {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                this.faq = JSON.parse(await response.text())
                console.log(this.faq)

            }
        },
        async getService(){
            let link = new URLSearchParams(window.location.search)
            let response = await fetch('/api/v1/services/'+link.get("service_id"), {
                method: 'GET' +
                    '',
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                this.service = JSON.parse(await response.text())
                console.log(this.service)

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
        await this.getService()
        await this.getServiceFaq()
    }
})