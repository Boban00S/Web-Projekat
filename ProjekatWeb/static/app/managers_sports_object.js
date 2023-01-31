Vue.component("managers-object", {
  data: function () {
    return {
      user: null,
      sportsObject: null,
      customers: null,
      trainers: null,
      trainings: null,
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
        <h4 clas="col-md-8">Offers</h4>
        <p>
        <li v-for="training in trainings">
            <router-link :to="{name:'edit-offer', params:{userId:user.id, oldTraining:training}}">{{training.name}}</router-link>
        </li>
        </p>
      </div>
      </div>
      <div class="container">
          <h2>Customers</h2>
          <table class="table">
          <thead class="thead-light">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">LastName</th>
              <th scope="col">Username</th>
            </tr>
          </thead>
          <tbody>
              <tr v-for="(customer, i) in customers">
                <th scope="row">{{i}}</th>
                <td>{{customer.name}}</td>
                <td>{{customer.lastName}}</td>
                <td>{{customer.username}}</td>
              </tr>
          </tbody>
        </table>
      </div>
      <div class="container">
      <h2>Trainers</h2>
      <table class="table">
      <thead class="thead-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Name</th>
          <th scope="col">LastName</th>
          <th scope="col">Username</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(trainer, i) in trainers">
            <th scope="row">{{i}}</th>
            <td>{{trainer.name}}</td>
            <td>{{trainer.lastName}}</td>
            <td>{{trainer.username}}</td>
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
      .get('rest/manager-object', { params: { id: this.$route.params.id } })
      .then(response => {
        this.sportsObject = response.data;
      });
    axios
      .get('rest/customers', { params: { id: this.$route.params.id } })
      .then(response => {
        this.customers = response.data;
      });
    axios
      .get('rest/trainers', { params: { id: this.$route.params.id } })
      .then(response => {
        this.trainers = response.data;
      })
    axios
        .get('rest/sport-object/trainings', { params: { id: this.$route.params.id } })
        .then(response => {
          this.trainings = response.data;
        })
  },
  methods:{
  }

});