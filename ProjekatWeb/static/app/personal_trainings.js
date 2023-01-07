Vue.component("personal-trainings", {
    data: function () {
        return {
            user: null,
            trainings: null,
        }
    },
    template:
        `
    <div v-if="trainings">
      <div class="container">
      <h2>Trainings</h2>
      <table class="table">
      <thead class="thead-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Training</th>
          <th scope="col">Object</th>
          <th scope="col">Date</th>
          <th scope="col">Cancel</th>
       
        </tr>
      </thead>
      <tbody>
          <tr v-for="(t, i) in trainings">
            <th scope="row">{{i}}</th>
            <td>{{t.name}}</td>
            <td>{{t.sportsObject.name}}</td>
            <td>{{t.trainingDate}}</td>
            <td v-if="t.canCancel"><div>
            <button type="button" class="btn btn-danger" v-on:click="deleteTraining(t)">Cancel</button>
            </div>
            </td>
          </tr>
      </tbody>
    </table>
  </div>
  </div>


    `
    ,
    mounted() {
        axios
            .get('rest/user', { params: { id: this.$route.params.id } })
            .then(response => {
                this.user = response.data;
            });
        axios
            .get('rest/personal-trainings', { params: { id: this.$route.params.id } })
            .then(response => {
                this.trainings = response.data;
                for(t of this.trainings){
                    this.canCancel(t);
                }
            });
    },
    methods:{
        canCancel: function (training){
            var current = new Date();
            var trainingDate = new Date(training.trainingDate);
            training.canCancel = current.getDay() - trainingDate.getDay() >= 2;
        },
        deleteTraining: function (training){
            axios
                .delete('rest/personal-training', {params: {trainingId: training.id, trainerId: this.user.id}})
                .then(response =>{
                    this.trainings = response.data;
                    for(t of this.trainings){
                        this.canCancel(t);
                    }
                })
        }
    }

});