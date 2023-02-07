Vue.component("edit-offer", {
    data: function (){
        return{
            user: null,
            sportsObject: null,
            file: null,
            oldTraining:null,
            newTraining:null,
            trainers: null,
            trainings: null
        }
    },
    template:
        `
  <div>
    <section class="vh-100 bg-image" style="background-image: url(Images/background-registration.jpg)">
    <div id="registration" class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100" >
                <div class="col-6 col-md-7 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5">Create a new offer</h2>

                            <form>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="form3Example1cg">
                                        Name</label>
                                    <input type="text" id="form3Example1cg" class="form-control border border-dark form-control-lg"
                                        name="objectName" v-model="newTraining.name" />
                                </div>

                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Type
                                  </label>
                                  <input type="text" v-model="newTraining.type" class="form-control border border-dark form-control-lg" aria-label="Default select example">
                                </div>

                                <div class="form-group form-outline mb-4">
                                  <label class="form-label" for="exampleFormControlFile1">Image</label>
                                  <input type="file" ref="myFile" @change="uploadFile"  class="form-control-file" id="exampleFormControlFile1">
                                </div>
                                
                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Description
                                  </label>
                                      <textarea class="form-control border border-dark form-control-lg" v-model="newTraining.description" id="newOffer.description" rows="3" placeholder="Enter a description of the offer (optional)"></textarea>
                                </div>
                                
                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Duration
                                  </label>
                                  <input type="number" v-model="newTraining.duration" class="form-control border border-dark form-control-lg" aria-label="Default select example">
                                </div>
                                
                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Trainers
                                  </label>
                                  <select v-model="newTraining.trainer" class="form-select border border-dark from-select-lg" aria-label="Default select example">
                                  <option :value="trainer" v-for="trainer in trainers">
                                    @{{trainer.username}}
                                  </option>
                                  </select>
                                </div>
                                
                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-primary" @click.prevent="addTraining">Submit</button>
                                </div>

                            </form>

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
            .get('rest/user', {params: {id: this.$route.params.userId}})
            .then(response => {
                this.user = response.data;
            });
        axios
            .get('rest/manager-object', {params: {id: this.$route.params.userId}})
            .then(response => {
                this.sportsObject = response.data;
            });
        axios
            .get('rest/sport-object/trainers', {params: {id: this.$route.params.userId}})
            .then(response => {
                this.trainers = response.data;
            });
        axios
            .get('rest/sport-object/trainings', {params: {id: this.$route.params.userId}})
            .then(response => {
                this.trainings = response.data;
            });
        this.oldTraining = this.$route.params.oldTraining;
        this.newTraining = this.$route.params.oldTraining;
    },
    methods:{
        uploadFile(){
            this.TrainingFile = this.$refs.myFile.files[0];
        },
        submitFile(){
            const formData = new FormData();
            formData.append('file', this.TrainingFile);
            formData.append('fileName', this.newTraining.name+"_"+this.sportsObject.name);
            const headers = { 'Content-Type': 'multipart/form-data'};
            axios.post('rest/offer/image', formData, {headers})
            this.newTraining.imagePath = "../images/"+this.newTraining.name+"_"+this.sportsObject.name+".jpg";

        },
        containsTraining(){
            for(const item of this.trainings){
                if(this.newTraining.name === item.name && this.newTraining.id !== item.id){
                    return true
                }
            }
            return false;
        },
        deleteOldTraining(){
            return axios
                .delete('rest/sport-object/training', {params: {objectId: this.sportsObject.id, trainingId: this.oldTraining.id}});
        },
        validateInputs(){
            return this.newTraining.name == null || this.newTraining.name === ""||
                this.newTraining.type == null || this.newTraining.type === "";
        },
        addTraining(){
            if(this.newTraining.name !== ""){
                this.contains = this.containsTraining()
                if(this.contains){
                    alert("Training is already added!");
                    return;
                }
                if(this.TrainingFile != null){
                    this.submitFile();
                }

                if(this.validateInputs()){
                    alert("Input not valid!");
                    return;
                }

                axios
                    .delete('rest/sport-object/training', {params: {objectId: this.sportsObject.id, trainingId: this.oldTraining.id}})
                    .then(response =>{
                        this.trainings = response.data;
                        axios
                            .post('rest/sport-object/training', this.newTraining)
                            .then(response => {
                                this.$router.push({path: '/user/' + this.user.id});
                            })
                    })
            }
        }
    }
})