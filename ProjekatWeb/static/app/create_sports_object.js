Vue.component("admin-object", {
  data: function () {
    return {
      sportsObject: {
        "averageGrade": 0.0,
        "workingHours": "16-24",
        "openingHours": {
          "fromHours": 16,
          "fromMinutes": 0,
          "toHours": 24,
          "toMinutes": 0
        }},
      location: {},
      objectType: {},
      file: null,
      availableManagers: null,
      showAddManager: false,
      manager: { gender: "female" },
      validInput: true,
      user: null
    }
  },
  template:
    `
    <div>
    <section class="vh-100 bg-image" style="background-image: url(Images/background-registration.jpg)">
    <div id="registration" class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="col d-flex justify-content-center align-items-center h-100">
                <div v-if="showAddManager" class="col-6 col-md-7 col-lg-7 col-xl-6">
                  <div class="card" style="border-radius:15px;">
                    <div class="card-body p-5">
                      <h2 class="text-uppercase text-center mb-5"> Create Manager</h2>
                        <form @submit="registrateManager">
                          <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example1cg">
                              First Name
                            </label>
                            <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                            name="firstname" v-model="manager.name" />
                          </div>
                          
                          <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example1cg">
                              Last Name
                            </label>
                            <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                            name="lastname" v-model="manager.lastName" />
                          </div>
                          
                          <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example1cg">
                              Username
                            </label>
                            <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                            name="username" v-model="manager.username" />
                          </div>

                          <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example4cg">Password</label>
                            <input type="password" id="form3Example4cg" name="password"
                              class="form-control form-control-lg border border-dark" v-model="manager.password" />
                          </div>

                          <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example3cg" name="">Birth Date</label>
                            <input type="date" min="1940-01-01" max="2013-01-01" id="form3Example3cg" class="form-control form-control-lg border border-dark" 
                               v-model="manager.birthdate" />
                          </div>

                          <div class="form-outline mb-4" name="gender">
                            <label class="form-label" for="form3Example4cg">Gender</label>
                            <br />
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                    id="femaleGender" value="female" checked v-model="manager.gender" />
                                <label class="form-check-label" for="femaleGender">Female</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                    id="maleGender" value="male" v-model="manager.gender" />
                                <label class="form-check-label" for="maleGender">Male</label>
                            </div>
                         </div>


                        </form>
                    </div>
                  </div>
                </div>
                <div class="col-6 col-md-7 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5">Create a sports object</h2>

                            <form>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example1cg">
                                        Name</label>
                                    <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                                        name="objectName" v-model="sportsObject.name" />
                                </div>

                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Type
                                  </label>
                                  <select v-model="objectType.name" class="form-select border border-dark form-select-lg" aria-label="Default select example">
                                    <option selected value="Gym">Gym</option>
                                    <option value="Sauna">Sauna</option>
                                    <option value="Pool">Pool</option>
                                  </select>
                                </div>

                              <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Managers
                                  </label>
                                  <div class="row align-items-center">
                                    <div v-if="!showAddManager" class="col-md-6">
                                      <select v-model="sportsObject.managerUsername" class="form-select border border-dark form-select-lg" aria-label="Default select example">
                                      <option :value="manager.username"  v-for="manager in availableManagers" >
                                          @{{manager.username}}
                                      </option>
                                      </select>
                                    </div>
                                    <div class="col-md-6">
                                      <button type="button" v-on:click="showAddManagerForm()" class="btn btn-primary btn-block btn-lg gradient-custom-4 text-body">Create Manager</button>
                                    </div>
                                  </div>
                              </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Street</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="street" v-model="location.street" />
                                </div>
                                
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Place</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="place" v-model="location.place" />
                                </div>
                                
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example3cg">Country</label>
                                    <input type="text" id="form3Example3cg" class="form-control border border-dark form-control-lg"
                                        name="country" v-model="location.country" />
                                </div>

                                <div class="form-group form-outline mb-4">
                                  <label class="form-label" for="exampleFormControlFile1">Logo</label>
                                  <input type="file" ref="myFile" @change="uploadFile"  class="form-control-file" id="exampleFormControlFile1">
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="button" v-on:click="createSportsObject()"
                                        class="btn btn-primary btn-block btn-lg gradient-custom-4 text-body">Create</button>
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
  mounted() {
    axios
      .get('rest/available/managers')
      .then(response => {
        this.availableManagers = response.data;
      });
    axios
        .get('rest/user', { params: { id: this.$route.params.id } })
        .then(response => {
          this.user = response.data;
        });
  },
  methods: {
    uploadFile() {
      this.Logo = this.$refs.myFile.files[0];
    },
    submitFile() {
      const formData = new FormData();
      formData.append('file', this.Logo);
      formData.append('fileName', this.sportsObject.name);
      const headers = { 'Content-Type': 'multipart/form-data' };
      axios.post('rest/object/image', formData, { headers })
    },
    showAddManagerForm() {
      this.showAddManager = !this.showAddManager;
    },
    registrateManager: function () {
      axios.post('rest/manager/registration', this.manager)
        .then((response) => {
          alert("Novi menadžer je uspešno kreiran")

        })
        .catch(error => {
          alert("Username is not available!");
          this.manager.username = null;
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
    },
    createSportsObject: function () {
      if (!this.validateInput()) {
        return;
      }
      if (this.showAddManager) {
        this.registrateManager();
        this.sportsObject.managerUsername = this.manager.username
      }
      if (this.manager.username == null && this.showAddManager) {
        return;
      }
      this.sportsObject.location = this.location;
      this.sportsObject.objectType = this.objectType;

      axios.post('rest/create/object', this.sportsObject)
        .then((response) => {
          alert("Novi sportski objekat je kreiran")
          this.$router.push({
            name: 'sports-object',
            params: {
              id: this.user.id
            }
          })
        })
      this.submitFile();
    },
  }
});