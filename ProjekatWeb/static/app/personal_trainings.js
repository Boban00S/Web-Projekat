Vue.component("personal-trainings", {
    data: function () {
        return {
            user: null,
            trainings: null,
            searchColumns:[
                {name:"Object Name"},
                {name: "Price"},
                {name: "Date"}
            ],
            searchBy: "Object Name",
            objectName: "",
            startPrice: 0,
            endPrice: 0,
            startDate: null,
            endDate: null,
            sortColumns:[
                {name: "Object Name"},
                {name: "Price"},
                {name: "Date"}
            ],
            sortColumn: "Object Name",
            ascending: true,
            filterBy: "",
            oldTrainings: null,
        }
    },
    template:
        `
    <div v-if="trainings">
      <div class="container">
      <h2>Trainings</h2>
      <div>
      <h5>Search:</h5>
        <select class="form-select" name="sel2" @change="searchTable($event)">
            <option :value="col.name" v-for="col in searchColumns">{{col.name}}</option>
        </select>
      </div>

      <div v-if="searchBy=='Object Name'">
      <div class="position-relative">
            <span class="position-absolute search"><i class="fa fa-search"></i></span>
            <input class="form-control w-100" @input="searchCenter()" v-model="objectName" style="border:4px solid #e3f2fd;"
                placeholder="Search by name">
      </div>
      </div>

      <div v-if="searchBy=='Price'">
      <div class="position-relative">
            <span class="position-absolute search"><i class="fa fa-search"></i></span>
            <input class="form-control w-100" type="number" @input="searchPrice()" v-model="startPrice" style="border:4px solid #e3f2fd;"
                placeholder="Start Price...">
      </div>
      <div class="position-relative">
            <span class="position-absolute search"><i class="fa fa-search"></i></span>
            <input class="form-control w-100" type="number" @input="searchPrice()" v-model="endPrice" style="border:4px solid #e3f2fd;"
                placeholder="End Price...">
      </div>
      </div>


      <div v-if="searchBy=='Date'">
      <div class="position-relative">
            <span class="position-absolute search"><i class="fa fa-search"></i></span>
            <input class="form-control w-100" type="date" @input="searchDate()" v-model="startDate" style="border:4px solid #e3f2fd;"
                placeholder="Search by...">
      </div>
      <div class="position-relative" v-if="searchBy=='Date'">
            <span class="position-absolute search"><i class="fa fa-search"></i></span>
            <input class="form-control w-100" type="date" @input="searchDate()" v-model="endDate" style="border:4px solid #e3f2fd;"
                placeholder="Search by...">
      </div>
      </div>
      <br>
      <div class="float-right">
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
                placeholder="Filter by object or training type...">
      </div>
      
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
            <td>{{t.date}}</td>
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
                this.oldTrainings = this.trainings;
            });
    },
    methods:{
        canCancel: function (training){
            var current = new Date();
            var trainingDate = new Date(training.date);
            training.canCancel = current.getDay() - trainingDate.getDay() <= -2;
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
        },
        searchTable(event){
            this.searchBy = event.target.value;
            this.trainings = this.oldTrainings;
            this.filterBy = "";
        },
        searchCenter(){
            this.trainings = this.oldTrainings.filter(t =>{
                const nameSportsObject = t.sportsObject.name.toString().toLowerCase();
                const typeSportsObject = t.sportsObject.objectType.name.toString().toLowerCase();
                const trainingType = t.type.toString().toLowerCase();
                const filterTerm = this.filterBy.toLowerCase();

                return (nameSportsObject.includes(this.objectName) && (typeSportsObject.includes(filterTerm) ||
                    trainingType.includes(filterTerm)));
            })
        },
        searchPrice(){
            this.trainings = this.oldTrainings.filter(t =>{
                const typeSportsObject = t.sportsObject.objectType.name.toString().toLowerCase();
                const trainingType = t.type.toString().toLowerCase();
                const filterTerm = this.filterBy.toLowerCase();
                trainingPrice = t.price;

                return trainingPrice >= this.startPrice && trainingPrice <= this.endPrice && (typeSportsObject.includes(filterTerm) ||
                    trainingType.includes(filterTerm));
            })
        },
        searchDate(){
            if(this.endDate == null){
                this.endDate = this.startDate;
            }
            this.trainings = this.oldTrainings.filter(t => {
                const typeSportsObject = t.sportsObject.objectType.name.toString().toLowerCase();
                const trainingType = t.type.toString().toLowerCase();
                const filterTerm = this.filterBy.toLowerCase();
                trainingDate = t.date;

                return trainingDate >= this.startDate && trainingDate <= this.endDate && (typeSportsObject.includes(filterTerm) ||
                    trainingType.includes(filterTerm));
            })
        },
        sortTable(event){
            if(event != null){
                this.sortColumn = event.target.value;
            }
            axios
                .get('rest/sort/trainer-trainings/personal', {params: {
                        id: this.$route.params.id,
                        sortColumn: this.sortColumn,
                        ascending: this.ascending
                    }})
                .then(response =>{
                    this.oldTrainings = response.data;
                    this.search()
                    // this.filterTable();
                })
        },
        search(){
            if(this.searchBy === "Object Name"){
                this.searchCenter();
            }else if(this.searchBy === "Price"){
                this.searchPrice();
            }else{
                this.searchDate();
            }
        },
        sortOrder(event){
            this.ascending = event.target.value === "ascending";
            this.sortTable(null);
        },
        filterTable(event){
            if(event != null){
                this.filterBy = event.target.value;
            }
            this.search();
            // this.trainings = this.trainings.filter(t => {
            //     const typeSportsObject = t.sportsObject.objectType.name.toString().toLowerCase();
            //     const trainingType = t.type.toString().toLowerCase();
            //     const filterTerm = this.filterBy.toLowerCase();
            //
            //     return typeSportsObject.includes(filterTerm) ||
            //         trainingType.includes(filterTerm)
            // })
        }
    }

});