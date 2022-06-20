Vue.component("registration", {
    data: function () {
        return {
            user: { gender: "female" },
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
                            <h2 class="text-uppercase text-center mb-5">Create an account</h2>

                            <form @submit='registrateUser'>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example1cg">First
                                        Name</label>
                                    <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                                        name="firstname" v-model="user.name" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Last
                                        Name</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="lastname" v-model="user.lastName" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Username</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="username" v-model="user.username" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example4cg">Password</label>
                                    <input type="password" id="form3Example4cg" name="password" 
                                        class="form-control form-control-lg border border-dark" v-model="user.password" />
                                </div>




                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg" name="">Birth Date</label>
                                    <input type="date" id="form3Example3cg" class="form-control form-control-lg border border-dark" 
                                        v-model="user.birthdate" />
                                </div>

                                <div class="form-outline mb-4" name="gender">
                                    <label class="form-label" for="form3Example4cg">Gender</label>
                                    <br />
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                            id="femaleGender" value="female" checked v-model="user.gender" />
                                        <label class="form-check-label" for="femaleGender">Female</label>
                                    </div>

                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                            id="maleGender" value="male" v-model="user.gender" />
                                        <label class="form-check-label" for="maleGender">Male</label>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="button" v-on:click="registrateUser()"
                                        class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
                                </div>

                                <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="#!"
                                        class="fw-bold text-body"><u>Login here</u></a></p>

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
    methods: {
        registrateUser: function () {
            if (!this.validateInput()) {
                return;
            }
            axios.post('rest/registration', this.user)
                .then((response) => {
                    this.$router.push({ name: 'homepage' })
                    alert("Novi korisnik je uspesno kreiran")
                })
                .catch(error => {
                    alert("Username is not available!");
                    this.user.username = null;
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
