const Homepage = { template: '<homepage></homepage>' }
const Login = { template: '<login></login>' }
const Registration = { template: '<registration></registration>' }


const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', name: 'homepage', component: Homepage },
        { path: '/login', name: 'login', component: Login },
        { path: '/registration', name: 'registration', component: Registration },
        { path: '/homepage/:username', name: 'homepage-login', component: Homepage},

    ]
});

var app = new Vue({
    router,
    el: "#homepage"
})