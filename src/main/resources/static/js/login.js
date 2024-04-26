let app = new Vue({
    mode: 'production',
    el: '#app',
    data: {
    },
    methods:{
        showPass(){
            let pass_eye = document.getElementById("pass-eye")
            let pass_input = document.getElementById("password")
            if (pass_eye.classList.contains("fa-eye-slash")){
                pass_eye.classList.remove("fa-eye-slash")
                pass_eye.classList.add("fa-eye")
                pass_input.type="text"
            }else{
                pass_eye.classList.remove("fa-eye")
                pass_eye.classList.add("fa-eye-slash")
                pass_input.type="password"

            }
        }
    }
})