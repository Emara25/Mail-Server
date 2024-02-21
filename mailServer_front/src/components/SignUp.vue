<template>
  
 <form class="container">
    <img src="hogwarts.png" style="width: 340px; height: 340px; cursor: pointer;  display:grid; place-items: center;">
    <form @submit.prevent="handleSubmit" class="innerContainer"> 
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" placeholder="@hogwarts.com"  required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div class="form-group">
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>
      <div class="btn">
         <button type="submit">Sign Up</button>
      </div>
    </form>
        <div class="rl">
          Already have an account?
          <router-link to="/">Sign In</router-link>
        </div>
      </form>
</template>

  <script>
  import axios from 'axios';
  export default {
    name:"SignUp",
    data() {
      return {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
      };
    },
    methods: {
      handleSubmit() {
        if (!this.email.endsWith('@hogwarts.com')) {
          alert('wrong email format');
          return;
        }
        else if (this.password !== this.confirmPassword) {
          alert('Password doesn\'t match');
          return;
        }
        axios.post("http://localhost:8080/user",{
          name: this.username,
          email: this.email,
          password: this.password,
        }).then(response =>{
          var x = response.data;
          if(!x){
            alert("email already exists");
          }
          else{
            this.getFiles();
            this.signUp();
          }
        }).catch(error => {
          alert(error);
          //this.signUp();
        }) 
      },checKIfBasic(value){
        var basicFolder= ['inbox.json', 'sent.json', 'draft.json', 'trash.json', 'contacts.json'];
        return basicFolder.includes(value);
      },
      signUp(){
          console.log('Signing up...', {
          username: this.username,
          email: this.email,
          password: this.password,
          id: this.id,
        });
        this.$store.commit('setSharedVariable',this.email);
        this.$router.push('/mail' );
      },getFiles(){
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
          }
        }).catch(error => {
          alert(error);
          //this.signIn();
        })
      },
  }
}
  ;
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
.innerContainer {
  display: grid;
  gap: 23px;
  place-items: center; 
}
.container {
  display: grid;
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
  background-color:#04920e;
}
router-link {
  color: black;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
  display: flex;
  justify-content: center;
}
.rl{
    display: flex;
    font-weight: bold;
    font-size: 18px;
    color:black ;
  }
</style>

  
