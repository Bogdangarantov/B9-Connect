const app = Vue.createApp({})

app.component('header-comp', {
    props: ['title'],
    template: `<h4>{{ title }}</h4>`
})

app.mount('#blog-post-demo')