Vue.component("br-sports-object", {
    data: function () {
        return {
            sportsObjects: null,
            error: '',
            searchText: '',
            filterText: '',
            sortColumns:[{name: "Name"},
                {name: "City"},
                {name: "Country"},
                {name: "Average Rate"}],
            sortColumn: "Name",
            searched: false,
            ascending: true,
        }
    },
    template: `
    <div>

        <div class="container mt-5 px-2">

            <div class="mb-2 d-flex justify-content-between align-items-center">

                <div class="position-relative">
                    <span class="position-absolute search"><i class="fa fa-search"></i></span>
                    <input class="form-control w-100" @input="searchCenter()" v-model="searchText" style="border:4px solid #e3f2fd;"
                        placeholder="Search by name, type...">
                </div>
                
                <div class="position-relative">
                    <span class="position-absolute search"><i class="fa fa-search"></i></span>
                    <input class="form-control w-100" v-model="filterText" style="border:4px solid #e3f2fd;"
                        placeholder="Filter by type, status...">
                </div>
            </div>
            
            <div class="float-right">
              <h5>Sort By:</h5>
              <select class="form-select" name="sel2" @change="sortTable($event)">
                <option :value="col.name" v-for="col in sortColumns">{{col.name}}</option>
              </select>
              
              <select class="form-select" name="sel3" @change="sortOrder($event)">
                <option value="ascending">Ascending</option>
                <option value="descending">Descending</option>
              </select>   
            </div>
            
            <div class="row">
                <div v-if="b.isOpen" v-for="(b, i) in sortedSportsObjects" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
                    <img :src="b.imagePath" class="card-img-top" alt="..."></img>
                    <div class="card-body">
                        <h4 class="card-title fw-bold">{{b.name}} - {{b.objectType.name}}</h4>
                        <p class="card-text">{{b.description}}</p>
                        <div class="container">
                            <div class="row fw-bold">
                            		Location:
                            </div>
                            <div class="row">
                            	{{b.location.street}}, {{b.location.place}}, {{b.location.country}}
                            </div>
                            <div class="row text-secondary fst-italic">
                            	{{b.location.longitude}}, {{b.location.latitude}}
                            </div>
                            <div class="row text-success fw-bold">
                                Working Hours: {{b.workingHours}}
                            </div>
                        </div>
                    </div>
                    <div class="row align-text-bottom justify-content-end">
                        <router-link :to="{name:'br-sport-object', params:{object:b}}" tag="button" class="btn btn-primary stretched-link">Show Sport Object</router-link>
                        <div class="fs-2 col-3">{{b.averageGrade}}</div>
                        <div class="fs-2 col-2"> <img src="../images/rate.png" width="20" height="20"></img></div>
                    </div>
                </div>
                                
                 <div v-if="b.isOpen==false" v-for="(b, i) in sortedSportsObjects" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
                    <img :src="b.imagePath" class="card-img-top" alt="..."></img>
                    <div class="card-body">
                        <h4 class="card-title fw-bold">{{b.name}} - {{b.objectType.name}}</h4>
                        <p class="card-text">{{b.description}}</p>
                        <div class="container">
                            <div class="row fw-bold">
                            		Location:
                            </div>
                            <div class="row">
                            	{{b.location.street}}, {{b.location.place}}, {{b.location.country}}
                            </div>
                            <div class="row text-secondary fst-italic">
                            	{{b.location.longitude}}, {{b.location.latitude}}
                            </div>
                            <div class="row text-danger fw-bold">
                                Working Hours: {{b.workingHours}}
                            </div>
                        </div>
                    </div>
                    <div class="row align-text-bottom justify-content-end">
                        <router-link :to="{name:'br-sport-object', params:{object:b}}" tag="button" class="btn btn-primary stretched-link">Show Sport Object</router-link>
                        <div class="fs-2 col-3">{{b.averageGrade}}</div>
                        <div class="fs-2 col-2"> <img src="../images/rate.png" width="20" height="20"></img></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
    ,
    mounted() {
        axios
            .get('rest/homepage')
            .then(response => {
                this.sportsObjects = response.data;
                for (SportsObject of this.sportsObjects) {
                    this.isOpen(SportsObject);
                }
            });
    }
    ,
    methods: {
        isOpen: function (SportsObject) {
            var current = new Date();
            SportsObject.isOpen = current.getHours() >= SportsObject.openingHours.fromHours && current.getHours() < SportsObject.openingHours.toHours;
        },
        sortTable(event){
            if(event != null){
                this.sortColumn = event.target.value;
            }
            axios
                .get('rest/sort/sports-objects', {params: {sortColumn: this.sortColumn, ascending: this.ascending}})
                .then(response =>{
                    this.sportsObjects = response.data;
                    for (SportsObject of this.sportsObjects) {
                        this.isOpen(SportsObject);
                    }
                })
        },
        searchCenter() {
            this.searched = this.searchText != ""
        },
        getStatusString(isOpen) {
            if(isOpen){
                return "open"
            }else{
                return "close"
            }
        },
        sortOrder(event){
            this.ascending = event.target.value === "ascending";
            this.sortTable(null);
        }
    },
    computed: {
        sortedSportsObjects() {
            if (this.sportsObjects === null) {
                return;
            }
            return this.sportsObjects.filter(SportsObject => {
                const nameSportsObject = SportsObject.name.toString().toLowerCase();
                const typeSportsObject = SportsObject.objectType.name.toString().toLowerCase();
                const placeSportsObject = SportsObject.location.place.toString().toLowerCase();
                const countrySportsObject = SportsObject.location.country.toString().toLowerCase();
                const averageGradeSportsObject = SportsObject.averageGrade.toString().toLowerCase();
                const statusSportsObject = this.getStatusString(SportsObject.isOpen);
                const searchTerm = this.searchText.toLowerCase();
                const filterTerm = this.filterText.toLowerCase();

                return (nameSportsObject.includes(searchTerm) ||
                        typeSportsObject.includes(searchTerm) ||
                        placeSportsObject.includes(searchTerm) ||
                        averageGradeSportsObject.includes(searchTerm) ||
                        countrySportsObject.includes(searchTerm)) &&
                    (typeSportsObject.includes(filterTerm) ||
                        statusSportsObject.includes(filterTerm));
            });
        },

    }
});