let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user: null,
        cookieValue: "",
        newUser: {
            name: "",
            login: "",
            password: "",
            email: "",
            roles: []
        },
        newService: {
            name: "",
            description: "",
            faq: [],
            users: []
        },
        blockUserIndex: 0,
        allUsers: []
    },
    methods: {
        async getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        },
        getUser() {
            let th = this
            let user = JSON.parse(document.getElementById("user").value)
            console.log(user)
            th.user = user
            user.value = ""
        },
        async getAllUsers() {
            let response = await fetch('/api/v1/users', {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                let data = JSON.parse(await response.text())
                this.allUsers = data
            }
        },
        async addUser() {
            let th = this
            let response = await fetch("/api/v1/users", {
                method: "POST",
                headers: {
                    'Content-type': "application/json",
                    'X-XSRF-TOKEN': this.cookieValue
                },
                body: JSON.stringify(th.newUser)
            })
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
        async blockUser() {

        }
    },
    async created() {
        this.getUser()
        this.cookieValue = await this.getCookie('XSRF-TOKEN')
        await this.getAllUsers()

    }
})