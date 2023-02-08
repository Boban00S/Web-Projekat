const Login = { template: '<login></login>' }
const Registration = { template: '<registration></registration>' }
const UserProfile = { template: '<user-profile></user-profile>' }
const UserHomepage = { template: '<user-homepage></user-homepage>' }
const Homepage = { template: '<homepage></homepage>' }

const SportsObjectsInfo = { template: '<sports-object></sports-object>' }
const BrSportsObjectsInfo = { template: '<br-sports-object></br-sports-object>' }
const SportObjectInfo = { template: '<sport-object></sport-object>' }
const BrSportObjectInfo = { template: '<br-sport-object></br-sport-object>' }
const ShowUsers = { template: '<show-users></show-users>' }
const ManagersSportsObject = { template: '<managers-object></managers-object>' }
const CreateSportsObject = { template: '<admin-object></admin-object>' }
const CreateEmployee = { template: '<create-employee></create-employee>' }
const AddOffer = {template: '<add-offer></add-offer>'}
const EditOffer = { template: '<edit-offer></edit-offer>'}
const CustomerTrainings = { template: '<show-customer-trainings></show-customer-trainings>'}
const PersonalTrainings = { template: '<personal-trainings></personal-trainings>' }
const NonPersonalTrainings = {template: '<non-personal-trainings></non-personal-trainings>'}
const ManagerTrainings = {template: '<manager-trainings></manager-trainings>'}
const Membership = {template: '<membership></membership>'}
const ShowMembership = {template: '<show-membership></show-membership>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        {
            path: '/',
            component: Homepage,
            children: [
                {path: '', component: BrSportsObjectsInfo, name: 'br-sports-object'},
                {path: 'br-sport-object', component: BrSportObjectInfo, name: 'br-sport-object'}
            ]},
        { path: '/login', name: 'login', component: Login },
        { path: '/registration', name: 'registration', component: Registration },
        {path: 'show-membership', component: ShowMembership, name: 'show-membership'},

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
                { path: 'personal-trainings', component: PersonalTrainings, name: 'personal-trainings'},
                {path: 'non-personal-trainings', component: NonPersonalTrainings, name: 'non-personal-trainings'},
                {path: 'manager-trainings', component: ManagerTrainings, name: 'manager-trainings'},
                {path: 'membership', component: Membership, name: 'membership'},
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
