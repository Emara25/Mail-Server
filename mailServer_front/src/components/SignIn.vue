<template>
    <form class="container">
      <img src="hogwarts.png" style="width: 350px; height: 350px; cursor: pointer;display:grid; place-items: center;">
      <form @submit.prevent="handleSignIn">
        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
        <div class="btn">
          <button type="submit">Sign In</button>
        </div>
      </form>
      <div class="rl">
        Don't have an account? 
        <router-link to="/signup">Sign Up</router-link>
        <br><br><br><br><br><br><br><br><br><br>
      </div>
    </form>
  </template>
  
  <script>
  import axios from 'axios';

  export default {
    name: 'SignIn',
    data() {
      return {
        email: '',
        password: '',
        id: 100,
      };
    },
    methods: {
      handleSignIn() {
        if (!this.email.endsWith('@hogwarts.com')) {
          alert('wrong email format');
          return;
        }
        axios.get("http://localhost:8080/user",{
          params:{
            email: this.email,
            password: this.password,
          }
        }).then(response =>{
          var x = response.data ;
          if(x.length == 0){
            alert("Wrong email or password");
          }
          else{
            var list=[];
            for(let i=0;i<x.length;i++){
              list.push( {value:x[i]} );
            }

            for (let index = list.length-1; index >=0 ; index--) {
              const element = list[index];
              if(this.checKIfBasic(element.value)){
                list.splice(index,1);
              }else{
                element.value = element.value.replace(".json", "");
              }
            }

            this.$store.commit("setFiles",list);
            this.signIn();
          }
        }).catch(error => {
          alert(error);
          this.signIn();
        })},
        checKIfBasic(value){
        var basicFolder= ['inbox.json', 'sent.json', 'draft.json', 'trash.json', 'contacts.json'];
        return basicFolder.includes(value);
      },
      signIn() {
        console.log('Signing in with:', this.email, this.password);
        this.$store.commit('setSharedVariable',this.email);
        this.$router.push('/mail');
      },
    },
  };
  </script>
<style scoped>
.container {
  min-height: 100%;
  min-width: 100%;
  align-items: center;
  justify-content: center;
  background-color: rgb(232, 192, 49);
}
.form-group {
  width: 300px;
  margin: auto;
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: rgb(31, 108, 77);
  color: #fff;
}

.btn {
  justify-content: center;
}

form {
  display: grid;
  gap: 27px;
  place-items: center;
}

label {
  display: block;
  font-size: 18px;
  font-weight: bold;
  color: #fff;
}

input {
  width: 100%;
  padding: 8px;
  margin-top: 4px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 18px;
  background-color: #ccc;
}

button {
  font-weight: bold;
  font-size: 18px;
  width: 150px;
  background-color: #1f6c4d;
  color: #fff;
  font-weight: bold;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #04920e;
}
router-link {
  color: black;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
  display: flex;
  justify-content: center;
}

.rl {
  display: flex;
  font-weight: bold;
  font-size: 18px;
  color: black;
}
</style>



  