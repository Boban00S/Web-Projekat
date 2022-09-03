Vue.component("managers-object", {
  data: function () {
    return {
      user: null,
      sportsObject: null,
    }
  },
  template:
    `
    <div
    class="bg-image p-5 text-center shadow-1-strong rounded mb-5 text-white"
    style="background-image: url('https://mdbcdn.b-cdn.net/img/new/slides/003.webp');"
  >
    <h1 class="mb-3 h2">{{sportsObject.name}}</h1>
  
    <p>
      {{sportsObject.description}}
    </p>
  </div>
    `
  ,
  mounted() {
    axios
      .get('rest/user', { params: { id: this.$route.params.id } })
      .then(response => {
        this.user = response.data;
      })
    this.$route.params.id;
    axios
      .get('rest/sportsobject', { params: { id: this.$route.params.id } })
      .then(response => {
        this.sportsObject = response.data;
      })
  },
});