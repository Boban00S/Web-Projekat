Vue.component("show-membership", {
  data: function () {
    return {
      customer: null,
      membership: null,
    }
  },
  template:
    `
       <div>
    <section class="vh-100 bg-image" style="background-image: url(Images/background-registration.jpg)">
    <div id="login" class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5">Membership Details</h2>

                            <form @submit='buyMembership'>


                                <div class="form-outline mb-4">
                                    <h4>Code: {{membership.code}}</h4>
                                </div>
                                
                                <div class="form-outline mb-4">
                                    <h5>Type: {{membership.membershipType}}</h5>
                                </div>
                                
                                <div class="form-outline mb-4">
                                    <h6>Price: {{membership.price}} - Daily Usage: {{membership.dailyUsage}}</h6>
                                </div>
                                
                                <div class="form-outline mb-4">
                                    <p><b>Description:</b> {{membership.description}}</p>
                                </div>
                                
<!--                                <div class="form-outline mb-4">-->
<!--                                    <label class="form-label" for="form3Example4cg">Password</label>-->
<!--                                    <input type="password" id="form3Example4cg" name="password"-->
<!--                                        class="form-control form-control-lg border border-dark" v-model="user.password" />-->
<!--                                </div>-->


                                <div class="d-flex justify-content-center">
                                    <button type="button" v-on:click="buyMembership()"
                                        class="btn btn-primary btn-block btn-lg gradient-custom-4 text-body">Buy</button>
                                </div>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
    </div>
   
    

    `
  ,
  mounted() {
    axios
      .get('rest/customer', { params: { id: this.$route.params.userId } })
      .then(response => {
        this.customer = response.data;
      });
    this.membership = this.$route.params.membership;
  },
  methods:{
    buyMembership: function (){
      this.membership.active = true;
      this.customer.membership = this.membership
      axios
          .post('rest/customer/membership', this.customer)
          .then(response =>{
            this.$router.push({ path: '/user/' + this.$route.params.userId})
          })
    }
  }

});