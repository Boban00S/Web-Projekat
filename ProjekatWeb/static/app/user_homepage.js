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
        </section>

        <router-view></router-view>
    </div>
    `
    ,
    mounted() {
        axios
            .get('rest/homepage')
            .then(response => {
                this.sportsObjects = response.data;
                for (SportsObject of this.sportsObjects) {
                    this.isOpen(SportsObject);
                }
            });
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
                    this.$router.push({ name: 'homepage' })
                });
        },
        isOpen: function (SportsObject) {
            var current = new Date();
            SportsObject.isOpen = current.getHours() >= SportsObject.openingHours.fromHours && current.getHours() < SportsObject.openingHours.toHours;
        },
        usersSettings: function () {
            this.$router.push({ name: 'user-profile', params: { id: this.user.id } })
        },
        showUsers: function () {
            this.$router.push({ name: 'show-users', params: { id: this.user.id } })
        }
    },
    computed: {
        filteredSportsObjects() {
            if (this.sportsObjects === null) {
                return;
            }
            return this.sportsObjects.filter(SportsObject => {
                const nameSportsObject = SportsObject.name.toString().toLowerCase();
                const typeSportsObject = SportsObject.objectType.name.toString().toLowerCase();
                const placeSportsObject = SportsObject.location.place.toString().toLowerCase();
                const countrySportsObject = SportsObject.location.country.toString().toLowerCase();
                const averageGradeSportsObject = SportsObject.averageGrade.toString().toLowerCase();
                const searchTerm = this.filter.toLowerCase();

                return nameSportsObject.includes(searchTerm) ||
                    typeSportsObject.includes(searchTerm) ||
                    placeSportsObject.includes(searchTerm) ||
                    averageGradeSportsObject.includes(searchTerm) ||
                    countrySportsObject.includes(searchTerm);
            });
        }
    }
});