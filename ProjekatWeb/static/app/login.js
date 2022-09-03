Vue.component("login", {
    data: function () {
        return {
            user: {},
            error: ''
        }
    },
    template: `
    <div>
    <section class="vh-100 bg-image" style="background-image: url(Images/background-registration.jpg)">
    <div id="login" class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5">Sign in</h2>

                            <form @submit='loginUser'>


                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Username</label>
                                    <input type="text" id="form3Example3cg" class="form-control form-control-lg border border-dark"
                                        name="username" v-model="user.username" />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example4cg">Password</label>
                                    <input type="password" id="form3Example4cg" name="password"
                                        class="form-control form-control-lg border border-dark" v-model="user.password" />
                                </div>


                                <div class="d-flex justify-content-center">
                                    <button type="button" v-on:click="loginUser()"
                                        class="btn btn-primary btn-block btn-lg gradient-custom-4 text-body">Sign
                                        in</button>
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
    methods: {
        loginUser: function () {
            if (!this.validateInput()) {
                return;
            }
            this.error = ""
            axios.post('rest/login', this.user)
                .then((response) => {
                    this.$router.push({ path: '/user/' + response.data })
                })
                .catch(error => {
                    alert("Wrong username or password!");
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
})