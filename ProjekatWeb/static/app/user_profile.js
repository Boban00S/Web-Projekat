Vue.component("user-profile", {
    data: function () {
        return {
            user: null,
        }
    },
    props: {

    }
    ,
    template: `
    <div class="container">
<div class="row justify-content-center">
    <div class="col-12 col-lg-10 col-xl-8 mx-auto">
        <h2 class="h3 mb-4 page-title">Settings</h2>
        <div class="my-4">
            <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="false">Profile</a>
                </li>
            </ul>
            <form>
                <div class="row mt-5 align-items-center">
                    <div class="col">
                        <div class="row align-items-center">
                            <div class="col-md-7">
                                <h4 class="mb-1">{{user.name}}, {{user.lastName}}</h4>
                            </div>
                        </div>
                        <div class="row mb-4">
                            <div class="col-md-7">
                                <p class="text-muted">
                                    {{user.role}}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <hr class="my-4" />
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="firstname">Firstname</label>
                        <input type="text" id="firstname" class="form-control form-control-lg border border-dark" v-model="user.name" />
                    </div>
                    <div class="form-group col-md-6">
                        <label for="lastname">Lastname</label>
                        <input type="text" id="lastname" class="form-control form-control-lg border border-dark" v-model="user.lastName" />
                    </div>
                    <div class="form-group col-md-6">
                        <label for="username">Username</label>
                        <input type="text" id="username" class="form-control form-control-lg border border-dark" v-model="user.username" />
                    </div>
                    
                    <div class="form-group col-md-6">
                        <label class="form-label" for="form3Example3cg" name="">Birth Date</label>
                        <input type="date" min="1940-01-01" max="2013-01-01" id="form3Example3cg" class="form-control form-control-lg border border-dark" 
                            v-model="user.birthdate" />
                    </div>

                    <div class="form-group col-md-6">
                        <label for="gender">Gender</label>
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
                </div>
                <hr class="my-4" />
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="inputPassword4">Old Password</label>
                            <input type="password" class="form-control form-control-lg border border-dark" id="inputPassword5" />
                        </div>
                        <div class="form-group">
                            <label for="inputPassword5">New Password</label>
                            <input type="password" class="form-control form-control-lg border border-dark" id="inputPassword5" />
                        </div>
                        <div class="form-group">
                            <label for="inputPassword6">Confirm Password</label>
                            <input type="password" class="form-control form-control-lg border border-dark" id="inputPassword6" />
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Save Change</button>
            </form>
        </div>
    </div>
</div>

</div>


    `
    ,
    mounted() {
        axios
            .get('rest/testlogin')
            .then(response => {
                this.user = response.data;
            })
            .catch((error) => {
                return;
            })
        var css_text =
            `
        body {
            color: #8e9194;
        }
        
        .avatar-xl img {
            width: 110px;
        }
        
        .rounded-circle {
            border-radius: 50% !important;
        }
        
        img {
            vertical-align: middle;
            border-style: none;
        }
        
        .text-muted {
            color: #aeb0b4 !important;
        }
        
        .text-muted {
            font-weight: 300;
        }
        
        .form-control {
            display: block;
            width: 100%;
            height: calc(1.5em + 0.75rem + 2px);
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
            font-weight: 400;
            line-height: 1.5;
            color: #4d5154;
            background-color: #ffffff;
            background-clip: padding-box;
            border: 1px solid #eef0f3;
            border-radius: 0.25rem;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }
        `;
        var css = document.createElement('style');
        css.type = 'text/css';
        css.setAttributeNode(document.createAttribute('scopped'));
        css.appendChild(document.createTextNode(css_text));
        this.$el.appendChild(css);

    }
})