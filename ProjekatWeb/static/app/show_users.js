Vue.component("show-users", {
  data: function () {
    return {
      users: null,
      oldUsers: null,
      searchColumns: [
        {name: "FirstName" },
        {name: "LastName"},
        {name: "Username"}
      ],
      searchText: "",
      searchByCol: "FirstName",
      sortColumns:[
        {name: "FirstName"},
        {name: "LastName"},
        {name: "Username"},
        {name: "Points"}
      ],
      sortColumn: "FirstName",
      ascending: true,
      filterBy: ""
    }
  },
  template:
    `
<div>
  <div class="container">
  <h2>Registered Users:</h2>
  <div>
  <h5>Search:</h5>
    <select class="form-select" name="sel2" @change="searchBy($event)">
        <option :value="col.name" v-for="col in searchColumns">{{col.name}}</option>
    </select>
    
  </div>
  <div class="position-relative">
        <span class="position-absolute search"><i class="fa fa-search"></i></span>
        <input class="form-control w-100" @input="searchTable" v-model="searchText" style="border:4px solid #e3f2fd;"
            placeholder="Search by name...">
  </div>
  <br>
    <div class="position-relative">
    <h5>Sort By:</h5>
          <select class="form-select" name="sel1" @change="sortTable($event)">
              <option :value="col.name" v-for="col in sortColumns">{{col.name}}</option>
          </select>      
    
          <select class="form-select" name="sel3" @change="sortOrder($event)">
              <option value="ascending">Ascending</option>
              <option value="descending">Descending</option>
          </select>      
    </div>
  <br>
      <div class="position-relative">
      <h5>Filter By:</h5>
            <span class="position-absolute search"><i class="fa fa-search"></i></span>
            <input class="form-control w-100" @input="filterTable($event)" v-model="filterBy" style="border:4px solid #e3f2fd;"
                placeholder="Filter by role or customer type...">
      </div>
</div>

  <div>
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
  </div>
</div>
    `
  ,
  mounted() {
    axios
      .get('rest/users')
      .then(response => {
        this.users = response.data;
        this.oldUsers = response.data;
        })
  },
  methods:{
    getCustomerType: function(user){
      if(user.customerType == null)
          return "No";
      else
          return user.customerType.name;
    },
    searchBy: function (event){
      this.searchByCol = event.target.value;
    },
    searchTable: function (){
        console.log("asd")
      if(this.searchByCol === "FirstName"){
        this.users = this.oldUsers.filter(u =>{
            const nameUser = u.name.toString().toLowerCase();
            const searchTerm = this.searchText.toLowerCase();
            const roleUser = u.role.toString().toLowerCase();
            const customerType = this.getCustomerType(u).toString().toLowerCase();
            const filterTerm = this.filterBy.toLowerCase();

          return nameUser.includes(searchTerm) && (roleUser.includes(filterTerm) || customerType.includes(filterTerm));
        })
      }else if(this.searchByCol === "LastName"){
        this.users = this.oldUsers.filter(u =>{
          const lastNameUser = u.lastName.toString().toLowerCase();
          const searchTerm = this.searchText.toLowerCase();
          const roleUser = u.role.toString().toLowerCase();
          const customerType = this.getCustomerType(u).toString().toLowerCase();
          const filterTerm = this.filterBy.toLowerCase();

          return lastNameUser.includes(searchTerm) && (roleUser.includes(filterTerm) || customerType.includes(filterTerm));
        })
      }else{
        this.users = this.oldUsers.filter(u =>{
          const usernameUser = u.username.toString().toLowerCase();
          const searchTerm = this.searchText.toLowerCase();
          const roleUser = u.role.toString().toLowerCase();
          const customerType = this.getCustomerType(u).toString().toLowerCase();
          const filterTerm = this.filterBy.toLowerCase();

          return usernameUser.includes(searchTerm) && (roleUser.includes(filterTerm) || customerType.includes(filterTerm));
        })
      }
    },
    sortTable: function (event){
      if(event != null){
        this.sortColumn = event.target.value;
      }
      axios
          .get('rest/sort/users', {params:{
              sortColumn: this.sortColumn,
              ascending: this.ascending
            }})
          .then(response =>{
            this.oldUsers = response.data;
            this.searchTable();
          })
    },
    sortOrder(event){
      this.ascending = event.target.value === "ascending";
      this.sortTable(null);
    },
    filterTable(event){
      if(event != null){
        this.filterBy = event.target.value;
      }
      this.searchTable();
    }
  }
});