Vue.component("homepage", {
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
                    </ul>
                    <form class="d-flex" v-if="mode=='Browse'">
                        <button class="btn btn-primary me-2" type="submit" v-on:click="registrateUser()">Sign
                            up</button>
                        <button class="btn btn-primary" type="submit" v-on:click="loginUser()">Sign in</button>
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
                    this.$router.push({ path: '/user/' + this.user.id })
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
        }
    }
});