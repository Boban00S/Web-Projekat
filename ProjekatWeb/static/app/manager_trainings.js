Vue.component("manager-trainings", {
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
        </tr>
      </thead>
      <tbody>
          <tr v-for="(t, i) in trainings">
            <th scope="row">{{i}}</th>
            <td>{{t.name}}</td>
            <td>{{t.sportsObject.name}}</td>
            <td>{{t.trainingDate}}</td>
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
            .get('rest/sport-object-trainings', { params: { id: this.$route.params.id } })
            .then(response => {
                this.trainings = response.data;
            });
    },
    methods:{
    }

});