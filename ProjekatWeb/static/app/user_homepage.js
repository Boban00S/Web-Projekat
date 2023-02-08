Vue.component("user-homepage", {
    data: function () {
        return {
            sportsObjects: null,
            user: null,
            userRole: "",
            error: '',
            mode: 'Browse',
            filter: '',
        }
    },
    template: `
    <div>
        <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
            <div class="container-fluid" id="index">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <router-link to="/" class="nav-link active" aria-current="page">Home</router-link>
                        </li>
                        <li class="nav-item" v-if="userRole=='administrator'">
                            <router-link :to="{name:'show-users', params:{id:user.id}}" class="nav-link active" aria-current="page">Active Users</router-link>
                        </li>
                        <li class="nav-item" v-if="userRole=='manager'">
                            <router-link :to="{name:'managers-object', params:{id:user.id}}" class="nav-link active" aria-current="page">Sports Object</router-link>
                        </li>
                        <li class="nav-item" v-if="userRole=='administrator'">
                            <router-link :to="{name:'admin-object', params:{id:user.id}}" class="nav-link active" aria-current="page">Create Sports Object</router-link>
                        </li>
                        <li class="nav-item" v-if="userRole=='administrator'">
                            <router-link :to="{name:'create-employee', params:{id:user.id}}" class="nav-link active" aria-current="page">Create Employee</router-link>
                        </li> 
                        <li class="nav-item" v-if="userRole=='manager'">
                            <router-link :to="{name:'add-offer', params:{id:user.id}}" class="nav-link active" aria-current="page">Add Offer</router-link>
                        </li>        
                        <li class="nav-item" v-if="userRole=='customer'">
                            <router-link :to="{name:'show-customer-trainings', params:{id:user.id}}" class="nav-link active" aria-current="page">Trainings</router-link>
                        </li>
                        <li class="nav-item" v-if="userRole=='trainer'">
                            <router-link :to="{name:'personal-trainings', params:{id:user.id}}" class="nav-link active" aria-current="page">Personal Trainings</router-link>
                        </li>
                        <li class="nav-item" v-if="userRole=='trainer'">
                            <router-link :to="{name:'non-personal-trainings', params:{id:user.id}}" class="nav-link active" aria-current="page">Group Trainings</router-link>
                        </li>  
                        <li class="nav-item" v-if="userRole=='manager'">
                            <router-link :to="{name:'manager-trainings', params:{id:user.id}}" class="nav-link active" aria-current="page">Trainings</router-link>
                        </li>  
                        <li class="nav-item" v-if="userRole=='customer'">
                            <router-link :to="{name:'membership', params:{id:user.id}}" class="nav-link active" aria-current="page">Buy Membership</router-link>
                        </li>  
                                                                    
                    </ul>
                    <form class="d-flex" v-if="mode=='Browse'">
                        <button class="btn btn-primary me-2" type="submit" v-on:click="registrateUser()">Sign
                            up</button>
                        <button class="btn btn-primary" type="submit" v-on:click="loginUser()">Sign in</button>
                    </form>
                    <button class ="btn btn-primary me-2" v-if="mode=='LoggedIn'" type="submit" v-on:click="usersSettings()">
                        <label class="fw-bold me-1" v-if="mode=='LoggedIn'">User </label>
                        <label class="me-4 fw-italic" v-if="mode=='LoggedIn'"> {{this.user.username}}</label>
                    </button>
                    <form class="d-flex" v-if="mode=='LoggedIn'">
                        <button class="btn btn-primary" type="submit" v-on:click="logoutUser()">Log out</button>
                    </form>


                </div>
            </div>
        </nav>

        <router-view></router-view>
    </div>
    `
    ,
    mounted() {
        axios
            .get('rest/testlogin')
            .then(response => {
                this.user = response.data;
                this.userRole = response.data.role;
                if (this.user != "No") {
                    this.mode = 'LoggedIn'
                }
            })
            .catch((error) => {
                return;
            })
    }
    ,
    methods: {
        loginUser: function () {
            this.$router.push({ name: 'login' })
        },
        registrateUser: function () {
            this.$router.push({ name: 'registration' })
        },
        logoutUser: function () {
            axios
                .get('rest/logout')
                .then(response => {
                    this.mode = 'Browse';
                    this.$router.push('/')
                });
        },
        usersSettings: function () {
            this.$router.push({ name: 'user-profile', params: { id: this.user.id } })
        },
        showUsers: function () {
            this.$router.push({ name: 'show-users', params: { id: this.user.id } })
        }
    }
});