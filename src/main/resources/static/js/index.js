let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user:null
    },
    methods:{
        getUser(){
            let th = this
            let user = JSON.parse(document.getElementById("user").value)
            th.user = user
            console.log(user)
            console.log(user.id)
            console.log(user.roles)
            user.value =""
        }
    },
    async created(){
        this.getUser()
    }
})