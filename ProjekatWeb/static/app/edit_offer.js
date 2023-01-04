Vue.component("edit-offer", {
    data: function (){
        return{
            user: null,
            sportsObject: null,
            file: null,
            oldOffer:null,
            newOffer:null,
            trainers: null
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
                                        name="objectName" v-model="newOffer.name" />
                                </div>

                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Type
                                  </label>
                                  <input type="text" v-model="newOffer.type" class="form-control border border-dark form-control-lg" aria-label="Default select example">
                                </div>

                                <div class="form-group form-outline mb-4">
                                  <label class="form-label" for="exampleFormControlFile1">Image</label>
                                  <input type="file" ref="myFile" @change="uploadFile"  class="form-control-file" id="exampleFormControlFile1">
                                </div>
                                
                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Description
                                  </label>
                                      <textarea class="form-control border border-dark form-control-lg" v-model="newOffer.description" id="newOffer.description" rows="3" placeholder="Enter a description of the offer (optional)"></textarea>
                                </div>
                                
                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Duration
                                  </label>
                                  <input type="number" v-model="newOffer.duration" class="form-control border border-dark form-control-lg" aria-label="Default select example">
                                </div>
                                
                                <div class="form-outline mb-4">
                                  <label class="form-label" for="form3Example1cg">
                                    Trainers
                                  </label>
                                  <select v-model="newOffer.trainer" class="form-select border border-dark from-select-lg" aria-label="Default select example">
                                  <option :value="trainers.username" v-for="trainer in trainers">
                                    @{{trainer.username}}
                                  </option>
                                  </select>
                                </div>
                                
                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-primary" @click.prevent="addOffer">Submit</button>
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
        this.oldOffer = this.$route.params.oldOffer;
        this.newOffer = this.$route.params.oldOffer;
    },
    methods:{
        uploadFile(){
            this.OfferFile = this.$refs.myFile.files[0];
        },
        submitFile(){
            const formData = new FormData();
            formData.append('file', this.OfferFile);
            formData.append('fileName', this.newOffer.name+"_"+this.sportsObject.name);
            const headers = { 'Content-Type': 'multipart/form-data'};
            axios.post('rest/offer/image', formData, {headers})
            this.newOffer.imagePath = "../images/"+this.newOffer.name+"_"+this.sportsObject.name+".jpg";

        },
        containsOffer(){
            for(const item of this.sportsObject.offers){
                if(this.newOffer.name === item.name && this.newOffer.id !== item.id){
                    return true
                }
            }
            return false;
        },
        deleteOldOffer(){
            return axios
                .delete('rest/sport-object/offer', {params: {objectId: this.sportsObject.id, offerId: this.oldOffer.id}});
        },
        addOffer(){
            if(this.newOffer.name !== ""){
                this.contains = this.containsOffer()
                if(this.contains){
                    alert("Offer is already added!");
                    return;
                }
                if(this.OfferFile != null){
                    this.submitFile();
                }
                axios
                    .delete('rest/sport-object/offer', {params: {objectId: this.sportsObject.id, offerId: this.oldOffer.id}})
                    .then(response =>{
                        this.sportsObject = response.data;
                        this.sportsObject.offers.push(this.newOffer);
                        axios
                            .put('rest/sport-object', this.sportsObject)
                            .then(response => {
                                this.$router.push({path: '/user/' + this.user.id});
                            })
                    })
                // this.sportsObject.offers.push(this.newOffer);
                // axios
                //     .put('rest/sport-object', this.sportsObject)
                //     .then(response => {
                //         this.$router.push({path: '/user/' + this.user.id});
                //     })
            }
        }
    }
})