Vue.component("sport-object", {
    data: function () {
        return {
            user: null,
            sportsObject: null,
            trainings: null
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
            <h1 class="mb-3 h1">{{sportsObject.name}}</h1>
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
        <div v-if="user.role=='customer' ">
  <div v-if="sportsObject.isOpen">
        <table class="table">
      <thead class="thead-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Training</th>
          <th scope="col">Object</th>
          <th scope="col">Date</th>       
        </tr>
      </thead>
      <tbody>
          <tr v-for="(t, i) in trainings">
            <th scope="row">{{i}}</th>
            <td>{{t.name}}</td>
            <td>{{t.sportsObject.name}}</td>
            <td>{{t.date}}</td>
            <td><button type="button" class="btn btn-primary" v-on:click="scheduleTraining(t)">Schedule</button> </td>
          </tr>
      </tbody>
    </table>
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
        axios
            .get('rest/sports-object/available-trainings', {params:{id: this.sportsObject.id}})
            .then(response =>{
                this.trainings = response.data;
            });


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

        }
    }
});