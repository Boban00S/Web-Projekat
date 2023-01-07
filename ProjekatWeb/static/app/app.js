const Homepage = { template: '<homepage></homepage>' }
const Login = { template: '<login></login>' }
const Registration = { template: '<registration></registration>' }
const UserProfile = { template: '<user-profile></user-profile>' }
const UserHomepage = { template: '<user-homepage></user-homepage>' }

const SportsObjectsInfo = { template: '<sports-object></sports-object>' }
const SportObjectInfo = { template: '<sport-object></sport-object>' }
const ShowUsers = { template: '<show-users></show-users>' }
const ManagersSportsObject = { template: '<managers-object></managers-object>' }
const CreateSportsObject = { template: '<admin-object></admin-object>' }
const CreateEmployee = { template: '<create-employee></create-employee>' }
const AddOffer = {template: '<add-offer></add-offer>'}
const EditOffer = { template: '<edit-offer></edit-offer>'}
const CustomerTrainings = { template: '<show-customer-trainings></show-customer-trainings>'}


const router = new VueRouter({
    mode: 'hash',
    routes: [
        {
            path: '/',
            component: UserHomepage,
            children: [
                {path: '', component: SportsObjectsInfo, name: 'sports-object'},
                {path: 'sport-object', component: SportObjectInfo, name: 'sport-object'}
            ]},
        { path: '/login', name: 'login', component: Login },
        { path: '/registration', name: 'registration', component: Registration },
        {
            path: '/user/:id',
            component: UserHomepage,
            children: [
                { path: '', component: SportsObjectsInfo, name: 'sports-object' },
                { path: 'profile', component: UserProfile, name: 'user-profile' },
                { path: 'users', component: ShowUsers, name: 'show-users' },
                { path: 'object', component: ManagersSportsObject, name: 'managers-object' },
                { path: 'create/object', component: CreateSportsObject, name: 'admin-object' },
                { path: 'create-employee', component: CreateEmployee, name: 'create-employee'},
                { path: 'sport-object', component: SportObjectInfo, name: 'sport-object'},
                { path: 'add-offer', component: AddOffer, name: 'add-offer'},
                { path: 'edit-offer', component: EditOffer, name: 'edit-offer'},
                { path: 'show-customer-trainings', component: CustomerTrainings, name: 'show-customer-trainings'},
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
