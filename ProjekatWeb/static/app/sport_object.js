Vue.component("sport-object", {
    data: function () {
        return {
            user: null,
            sportsObject: null,
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
                <div class="row text-danger fw-bold">
                    Working Hours: {{sportsObject.workingHours}}
                </div>
                <div class="row align-text-bottom justify-content-end">
                    <div class="fs-2">{{sportsObject.averageGrade}} <img src="../images/rate.png" width="30" height="30"></div>
                </div>
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
        axios
            .get('rest/sport-object', { params: { id: this.$route.params.objectId } })
            .then(response => {
                this.sportsObject = response.data;
            });
    },
});