Vue.component("membership", {
    data: function () {
        return {
            memberships: null,
            user: null,
            error: '',
            mode: 'Browse',
        }
    },
    template: `
    <div>
        </section>

        <div class="container mt-5 px-2">
         
            
            <div class="row">
                <div v-for="(m, i) in memberships" :key="i" class="col-xs-6 card m-3" style="width: 18rem;">
                    <div class="card-body">
                        <h4 class="card-title fw-bold">Code: {{m.code}} </h4>
                        <h6 class="card-title fw-bold">Type: {{m.membershipType}}</h6>
                        <h7 class="card-title fw-bold">Price: {{m.price}} - Daily Usage: {{m.dailyUsage}}</h7>
                        <p class="card-text">{{m.description}}</p>
                    </div>
                    <div class="row align-text-bottom justify-content-end">
                        <router-link :to="{name:'show-membership', params:{userId:user.id, membership:m}}" tag="button" class="btn btn-primary stretched-link">Show</router-link>
                    </div>
                </div>                                
            </div>
        </div>
    </div>
    `
    ,
    mounted() {
        axios
            .get('rest/memberships')
            .then(response => {
                this.memberships = response.data;
            });
        axios
            .get('rest/user', { params: { id: this.$route.params.id } })
            .then(response => {
                this.user = response.data;
            });
    }
    ,
    methods: {}
});