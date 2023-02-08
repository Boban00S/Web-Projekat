Vue.component("create-employee", {
    data: function () {
        return {
            user: null,
            newUser: { gender: "female", role: "manager" },
            error: ''
        }
    },
    template: `
    <div>
    <section class="vh-100 bg-image" style="background-image: url(Images/background-registration.jpg)">
    <div id="registration" class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5">Create an employee</h2>

                            <form @submit='registrateUser'>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example1cg">First
                                        Name</label>
                                    <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                                        name="firstname" v-model="newUser.name" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Last
                                        Name</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="lastname" v-model="newUser.lastName" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Username</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="username" v-model="newUser.username" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example4cg">Password</label>
                                    <input type="password" id="form3Example4cg" name="password" 
                                        class="form-control form-control-lg border border-dark" v-model="newUser.password" />
                                </div>
                                
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg" name="">Birth Date</label>
                                    <input type="date" min="1940-01-01" max="2013-01-01" id="form3Example3cg" class="form-control form-control-lg border border-dark" 
                                        v-model="newUser.birthdate" />
                                </div>

                                <div class="form-outline mb-4" name="gender">
                                    <label class="form-label" for="form3Example4cg">Gender</label>
                                    <br />
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                            id="femaleGender" value="female" checked v-model="newUser.gender" />
                                        <label class="form-check-label" for="femaleGender">Female</label>
                                    </div>

                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                            id="maleGender" value="male" v-model="newUser.gender" />
                                        <label class="form-check-label" for="maleGender">Male</label>
                                    </div>
                                </div>
                                
                                <div class="form-outline mb-4" name="role">
                                    <label class="form-label" for="form3Example4cg">Role</label>
                                    <br />
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inlineRadioOptions2"
                                            id="managerRole" value="manager" checked v-model="newUser.role" />
                                        <label class="form-check-label" for="femaleGender">Manager</label>
                                    </div>

                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inlineRadioOptions2"
                                            id="trainerRole" value="trainer" v-model="newUser.role" />
                                        <label class="form-check-label" for="maleGender">Trainer</label>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="button" v-on:click="registrateUser()"
                                        class="btn btn-primary btn-block btn-lg gradient-custom-4 text-body">Register</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
    </div>
    `
    ,
    mounted(){
        axios
            .get('rest/user', { params: { id: this.$route.params.id } })
            .then(response => {
                this.user = response.data;
            });
    },
    methods: {

        registrateUser: function () {
            if (!this.validateInput()) {
                return;
            }
            axios.post('rest/create-employee', this.newUser)
                .then((response) => {
                    this.$router.push({ path: '/user/' + this.user.id })
                    alert("Novi korisnik je uspesno kreiran")
                })
                .catch(error => {
                    alert("Username is not available!");
                    this.newUser.username = null;
                    let field = document.getElementsByName("username")[0];
                    field.setAttribute('style', 'border:1px solid red !important');
                })
        },
        validateInput: function () {
            let output = true;

            for (field of document.getElementsByTagName("input")) {
                if (!field.value) {
                    field.setAttribute('style', 'border:1px solid red !important');
                    output = false;
                } else {
                    field.style.border = '1px solid black'
                }
            }

            return output;
        }
    }
});
