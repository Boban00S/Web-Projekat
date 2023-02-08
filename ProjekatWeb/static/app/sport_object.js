Vue.component("sport-object", {
    data: function () {
        return {
            user: null,
            sportsObject: null,
            trainings: null,
            todayDate: new Date().toISOString().split('T')[0],
            trainingDate: new Date().toISOString().split('T')[0]
        }
    },
    template:
        `
    <div v-if="sportsObject">
      <div class="row">
          <div class="col-md-4">    
            <img class="img-thumbnail" :src="sportsObject.imagePath" >    </img>
          </div>
          <div class="col-md-8">
            <h1 class="mb-3 h1">{{sportsObject.name}} - {{sportsObject.objectType.name}}</h1>
            <p>
              {{sportsObject.description}}
            </p>
            <div class="container">
                <div class="row fw-bold">
                        Location:
                </div>
                <div class="row">
                    {{sportsObject.location.street}}, {{sportsObject.location.place}}, {{sportsObject.location.country}}
                </div>
                <div class="row text-secondary fst-italic">
                    {{sportsObject.location.longitude}}, {{sportsObject.location.latitude}}
                </div>
                <div v-if="!sportsObject.isOpen" class="row text-danger fw-bold">
                    Working Hours: {{sportsObject.workingHours}}
                </div>
                <div v-if="sportsObject.isOpen" class="row text-success fw-bold">
                    Working Hours: {{sportsObject.workingHours}}
                </div>
                <div class="row align-text-bottom justify-content-end">
                    <div class="fs-2">{{sportsObject.averageGrade}} <img src="../images/rate.png" width="30" height="30"></div>
                </div>
            </div>
          </div>
      </div>
<!--        <div v-if="user.role=='customer' ">-->
<!--  <div v-if="sportsObject.isOpen">-->
<!--        <table class="table">-->
<!--      <thead class="thead-light">-->
<!--        <tr>-->
<!--          <th scope="col">#</th>-->
<!--          <th scope="col">Training</th>-->
<!--          <th scope="col">Object</th>-->
<!--          <th scope="col">Date</th>       -->
<!--        </tr>-->
<!--      </thead>-->
<!--      <tbody>-->
<!--          <tr v-for="(t, i) in trainings">-->
<!--            <th scope="row">{{i}}</th>-->
<!--            <td>{{t.name}}</td>-->
<!--            <td>{{t.sportsObject.name}}</td>-->
<!--            <td>{{t.date}}</td>-->
<!--            <td><button type="button" class="btn btn-primary" v-on:click="scheduleTraining(t)">Schedule</button> </td>-->
<!--          </tr>-->
<!--      </tbody>-->
<!--    </table>-->
<!--    </div>-->
<!--</div>-->
<!--    <div class="container">-->
        
        <div class="row">
        <div class="form-outline mb-4">
            <h4 class="form-label" for="form3Example3cg" name="">Date:</h4>
            <input type="date" id="dt" :min="todayDate" v-model="trainingDate" name="setTodaysDate" class="form-control form-control-lg border border-dark" 
                @change="getTrainingOnDate" />
        </div>
        <div v-for="(t, i) in trainings" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
        <img :src="t.imagePath" class="card-img-top" alt="..."></img>
        <div class="card-body">
            <h4 class="card-title fw-bold" v-if="t.trainer">Trainer: {{t.trainer.name}} {{t.trainer.lastName}}</h4>
            <p class="card-text">{{t.description}}</p>
        </div>
        <div class="row align-text-bottom justify-content-end">
            <button v-if="user.role=='customer'" type="button" class="btn btn-primary" v-on:click="scheduleTraining(t)">Schedule</button> 
            <div class="fs-2 col-3">{{t.price}}</div>
            <div class="fs-2 col-2"> <img src="../images/eurSign.png" width="20" height="20"></img></div>
        </div>
    </div>
    </div>
 
        
  </div>


    `
    ,
    mounted() {
        if(this.$route.params.userId){
            axios
                .get('rest/user', { params: { id: this.$route.params.userId } })
                .then(response => {
                    this.user = response.data;
                });
        }
        this.sportsObject = this.$route.params.object;
        // axios
        //     .get('rest/sports-object/available-trainings', {params:{id: this.sportsObject.id}})
        //     .then(response =>{
        //         this.trainings = response.data;
        //     });
        axios
            .get('rest/sport-object/trainings/date', {params:{id: this.sportsObject.id, date:this.trainingDate}})
            .then(response =>{
                this.trainings = response.data;
            })
    },
    methods:{
        scheduleTraining: function (training){
            axios
                .get('rest/customer', {params: {id: this.user.id}})
                .then(response =>{
                    this.customer = response.data;
                    this.trainingHistory = {"customer": this.customer, "trainer": training.trainer, "training": training}
                    axios
                        .post('rest/training-history', this.trainingHistory)
                        .then(response =>{
                            this.$router.push({path: '/user/' + this.user.id});
                        })
                        .catch(error => {
                            alert("Daily Usage Left is 0 or Membership is not active!");
                        })
                })

        },
        getTrainingOnDate: function(){
            axios
                .get('rest/sport-object/trainings/date', {params:{id: this.sportsObject.id, date:this.trainingDate}})
                .then(response =>{
                    this.trainings = response.data;
                })
        }
    }
});