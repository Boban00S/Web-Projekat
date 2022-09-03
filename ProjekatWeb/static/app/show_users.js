Vue.component("show-users", {
  data: function () {
    return {
      users: null,
    }
  },
  template:
    `
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
      <tr v-for="(user, i) in users">
        <th scope="row">{{i}}</th>
        <td>{{user.name}}</td>
        <td>{{user.lastName}}</td>
        <td>{{user.username}}</td>
      </tr>
  </tbody>
</table>

    `
  ,
  mounted() {
    axios
      .get('rest/users')
      .then(response => {
        this.users = response.data;
      })
  },
});