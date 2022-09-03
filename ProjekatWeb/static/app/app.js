const Homepage = { template: '<homepage></homepage>' }
const Login = { template: '<login></login>' }
const Registration = { template: '<registration></registration>' }
const UserProfile = { template: '<user-profile></user-profile>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', name: 'homepage', component: Homepage },
        { path: '/login', name: 'login', component: Login },
        { path: '/registration', name: 'registration', component: Registration },
        {
            path: '/user/:id',
            component: Homepage,
            name: 'homepage-login',
        },
        { path: '/user/:id/profile', component: UserProfile, name: 'user-profile' },
    ]
});

var app = new Vue({
    router,
    el: "#homepage"
})

// zameniti /homepage/:username na /:username/homepage
// dodati na to /:username/settings

// { path: '/homepage/:username', name: 'homepage-login', component: Homepage },
// { path: '/settings/:username', name: 'settings', component: Settings },
