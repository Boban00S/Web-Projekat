Vue.component("homepage", {
    data: function () {
        return {
            buildings: null,
            user: null,
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
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Locations</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Pricing</a>
                        </li>
                    </ul>
                    <form class="d-flex" v-if="mode=='Browse'">
                        <button class="btn btn-primary me-2" type="submit" v-on:click="registrateUser()">Sign
                            up</button>
                        <button class="btn btn-primary" type="submit" v-on:click="loginUser()">Sign in</button>
                    </form>
                    <label class="fw-bold me-1" v-if="mode=='LoggedIn'">User </label>
                    <label class="me-4 fw-italic" v-if="mode=='LoggedIn'"> {{this.user.username}}</label>
                    <form class="d-flex" v-if="mode=='LoggedIn'">
                        <button class="btn btn-primary" type="submit" v-on:click="logoutUser()">Log out</button>
                    </form>


                </div>
            </div>
        </nav>
        </section>

        <div class="container mt-5 px-2">

            <div class="mb-2 d-flex justify-content-between align-items-center">

                <div class="position-relative">
                    <span class="position-absolute search"><i class="fa fa-search"></i></span>
                    <input class="form-control w-100" v-model="filter" style="border:4px solid #e3f2fd;"
                        placeholder="Search by name, type...">
                </div>

            </div>
            <div class="row">
                <div v-for="(b, i) in filteredBuildings" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
                    <img :src="b.imagePath" class="card-img-top" alt="..."></img>
                    <div class="card-body">
                        <h4 class="card-title fw-bold">{{b.name}}</h4>
                        <p class="card-text">{{b.content}}</p>
                        <div class="container">
                            <div class="row">
                                Location: {{b.location}}
                            </div>
                            <div class="row">
                                Working Hours: {{b.workingHours}}
                            </div>
                        </div>
                        </div>
                    <div class="row align-text-bottom justify-content-end">
                        <div class="fs-2 col-3">{{b.averageGrade}}</div>
                        <div class="fs-2 col-2"> <img src="../images/rate.png" width="25" height="25"></img></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
    ,
    mounted() {
        axios
            .get('rest/homepage')
            .then(response => {
                this.buildings = response.data;
            });
        axios
            .get('rest/testlogin')
            .then(response => {
                this.user = response.data;
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
        }
    },
    computed: {
        filteredBuildings() {
            if (this.buildings === null) {
                return;
            }
            return this.buildings.filter(building => {
                const nameBuilding = building.name.toString().toLowerCase();
                const typeBuilding = building.type.name.toString().toLowerCase();
                const locationBuilding = building.location.toString().toLowerCase();
                const averageGradeBuilding = building.averageGrade.toString().toLowerCase();
                const searchTerm = this.filter.toLowerCase();

                return nameBuilding.includes(searchTerm) ||
                    typeBuilding.includes(searchTerm) ||
                    locationBuilding.includes(searchTerm) ||
                    averageGradeBuilding.includes(searchTerm);
            });
        }
    }
});