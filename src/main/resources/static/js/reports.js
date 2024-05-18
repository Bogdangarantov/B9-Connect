let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
        user: null,
        cookieValue: "",
        tickets: []
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
        async getTickets() {
            let response = await fetch("/api/v1/services/ticket", {
                method: "GET",
                headers: {
                    'X-XSRF-TOKEN': this.cookieValue
                }
            })
            if (response.ok) {
                this.tickets = JSON.parse(await response.text())
                console.log(this.tickets)
            }
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
        }
    },
    async created() {
        this.cookieValue = this.getCookie('XSRF-TOKEN')
        this.getUser()
        await this.getTickets()
    }
})