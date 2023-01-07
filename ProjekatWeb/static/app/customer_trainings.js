Vue.component("show-customer-trainings", {
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
          <th scope="col">Last 30 days</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(t, i) in trainings">
            <th scope="row">{{i}}</th>
            <td>{{t.training.name}}</td>
            <td>{{t.training.sportsObject.name}}</td>
            <td v-if="t.last30Days">
                <div v-for="date in t.last30Days">
                    {{ date }}                
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
            .get('rest/trainings-month', { params: { id: this.$route.params.id } })
            .then(response => {
                this.trainings = response.data;

            });
    },
    methods:{
    }

});