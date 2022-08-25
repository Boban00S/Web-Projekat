Vue.component("homepage", {
    data: function () {
        return {
            sportsObjects: null,
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
                <div v-if="b.isOpen" v-for="(b, i) in filteredSportsObjects" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
                    <img :src="b.imagePath" class="card-img-top" alt="..."></img>
                    <div class="card-body">
                        <h4 class="card-title fw-bold">{{b.name}} - {{b.objectType.name}}</h4>
                        <p class="card-text">{{b.description}}</p>
                        <div class="container">
                            <div class="row fw-bold">
                            		Location:
                            </div>
                            <div class="row">
                            	{{b.location.street}}, {{b.location.place}}, {{b.location.country}}
                            </div>
                            <div class="row text-secondary fst-italic">
                            	{{b.location.longitude}}, {{b.location.latitude}}
                            </div>
                            <div class="row text-success fw-bold">
                                Working Hours: {{b.workingHours}}
                            </div>
                        </div>
                        </div>
                    <div class="row align-text-bottom justify-content-end">
                        <div class="fs-2 col-3">{{b.averageGrade}}</div>
                        <div class="fs-2 col-2"> <img src="../images/rate.png" width="20" height="20"></img></div>
                    </div>
                </div>
                                
                 <div v-if="b.isOpen==false" v-for="(b, i) in filteredSportsObjects" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
                    <img :src="b.imagePath" class="card-img-top" alt="..."></img>
                    <div class="card-body">
                        <h4 class="card-title fw-bold">{{b.name}} - {{b.objectType.name}}</h4>
                        <p class="card-text">{{b.description}}</p>
                        <div class="container">
                            <div class="row fw-bold">
                            		Location:
                            </div>
                            <div class="row">
                            	{{b.location.street}}, {{b.location.place}}, {{b.location.country}}
                            </div>
                            <div class="row text-secondary fst-italic">
                            	{{b.location.longitude}}, {{b.location.latitude}}
                            </div>
                            <div class="row text-danger fw-bold">
                                Working Hours: {{b.workingHours}}
                            </div>
                        </div>
                        </div>
                    <div class="row align-text-bottom justify-content-end">
                        <div class="fs-2 col-3">{{b.averageGrade}}</div>
                        <div class="fs-2 col-2"> <img src="../images/rate.png" width="20" height="20"></img></div>
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
                this.sportsObjects = response.data;
                for(SportsObject of this.sportsObjects){
					this.isOpen(SportsObject);
				}
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
        },
        logoutUser: function(){
			axios
				.get('rest/logout')
				.then(response =>{
					this.mode = 'Browse';
				});
		},
        isOpen: function(SportsObject){
			var current = new Date();
			SportsObject.isOpen = current.getHours() >= SportsObject.openingHours.fromHours && current.getHours() < SportsObject.openingHours.toHours;
		},
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
                    averageGradeSportsObject.includes(searchTerm)||
                    countrySportsObject.includes(searchTerm);
            });
        }
    }
});