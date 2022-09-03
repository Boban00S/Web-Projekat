const Homepage = { template: '<homepage></homepage>' }
const Login = { template: '<login></login>' }
const Registration = { template: '<registration></registration>' }
const UserProfile = { template: '<user-profile></user-profile>' }
const UserHomepage = { template: '<user-homepage></user-homepage>' }

const SportsObjectsInfo = { template: '<sports-object></sports-object>' }
const ShowUsers = { template: '<show-users></show-users>' }
const ManagersSportsObject = { template: '<managers-object></managers-object>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', name: 'homepage', component: Homepage },
        { path: '/login', name: 'login', component: Login },
        { path: '/registration', name: 'registration', component: Registration },
        {
            path: '/user/:id',
            component: UserHomepage,
            children: [
                { path: '', component: SportsObjectsInfo, name: 'sports-object' },
                { path: 'profile', component: UserProfile, name: 'user-profile' },
                { path: 'users', component: ShowUsers, name: 'show-users' },
                { path: 'object', component: ManagersSportsObject, name: 'managers-object' }
            ]
        },
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
