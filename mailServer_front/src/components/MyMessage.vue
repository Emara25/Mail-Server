<template>
    <button @click="openMessage = true" class="side-btn">Compose</button>        
        <div v-if="openMessage" class="modal2">
            <div class="p">New Message</div>
            <br>
            <div class="rate-container">
                <select v-model="message.priority">
                  <option v-for="priority in [0,1,2,3,4]" :key="priority" :value="priority">{{ priority }}</option>
                </select>
              </div>
            <div v-for="idx in message.priority" :key="idx" class="star-container" style="display: inline-block;"> 
              <img src="star.png" style="height: 25px; width:25px;">
            </div>
            <br><br>
          <div class="reciever-input">
          <input v-model="reciever" type="to" id="reciever" placeholder="To::"/>
          </div>
          <div class="subject-input">
            <input v-model="subject" type="subject" id="subject" placeholder="Subject"/>
          </div>
          <div class="body-input">
            <textarea v-model="messageBody" id="message-body" placeholder="Enter the message">
            </textarea>
            <div v-if="files.length > 0" class="attachments">
                <h2>Attached Files:</h2>
                <ul>
                  <li v-for="(file, index) in files" :key="index">
                    <a :href="file.url" :download="file.name">
                      {{ file.name }} ({{ formatFileSize(file.size) }})
                    </a>
                    <button @click="removeFile(index)">Remove</button>
                  </li>
                </ul>
              </div>
          </div>
          <button class="cbtn" @click="sendDraft(),clear(), openMessage = false">Close</button>
          <img src="attach.png" class="attachBtn" @click="onPickFile">
          <input
            type="file"
            ref="fileInput"
            style="display: none;"
            accept="/*"
            @change="handleFileChange" multiple/>
          <button class="sbtn" @click="sendMessage(),clear() ,openMessage=false">Send</button>
        </div>
  </template>

  <script>
  import axios from 'axios'
  export default {
      data() {
        return {
          openMessage: false,
          reciever:'',
          subject:'',
          messageBody: '',
          message:{
            id: null,
            priority:0,
            from:this.$store.state.email,
            to:'',
            subject:'',
            body: '',
          date:'',
            attachments:[],
            formData:null,
          },
          formData:null,
          files:[],
        };
      },
      methods: {

        sendMessage(){
          this.message.to=this.reciever;
          this.message.subject=this.subject;
          this.message.body=this.messageBody;
          this.message.date= this.date;
          this.message.formData = this.formData;
          this.message.attachments=this.files; 

          console.log("Sending ...\n",
          "from: "+this.message.from,
          "\nto: "+this.message.to,
          "\nsubject: "+this.message.subject,
          "\nmessage: "+this.message.body,
          "\npriority: "+this.message.priority,
          "\ndate and time: "+this.message.date,
          "\nattachments:"+this.message.attachments,
          "\nformData:"+this.message.formData);

          const url = `http://localhost:8080/email`;

          axios.post(url,this.message
            ).then(response => {
              console.log('message sent successfully:', response.data);
            })
            .catch(error => {
              alert(error);
            });
        },
        sendDraft(){
            this.message.to=this.reciever;
            this.message.subject=this.subject;
            this.message.body=this.messageBody;
            this.message.date= this.date;
            this.message.formData = this.formData;
            this.message.attachments=this.files;
          if(this.messageBody!=''){
            console.log("drafting ...\n",
            "from: "+this.message.from,
            "\nto: "+this.message.to,
            "\nsubject: "+this.message.subject,
            "\npriority: "+this.message.priority,
            "\nmessage: "+this.message.body,
            "\ndate and time: "+this.message.date,
            "\nattachments:"+this.message.attachments,
            "\nformData:"+this.message.formData);

            axios.post(`http://localhost:8080/email/draft`,this.message
            ).then(response => {
              console.log('message drafted successfully:', response.data);
            })
            .catch(error => {
              alert(error);
            }); 
          }
      },
      onPickFile () {
        this.$refs.fileInput.click()
      },
      handleFileChange(event) {
      this.files = Array.from(event.target.files);
      const formData = new FormData();
      this.files.forEach((file, index) => {
        formData.append(`file${index+1}`, file);
      });
      this.formData = formData;
    },
    formatFileSize(size) {
      const units = ['B', 'KB', 'MB', 'GB'];
      let i = 0;
      while (size >= 1024 && i < units.length - 1) {
        size /= 1024;
        i++;
      }
      return size.toFixed(2) + ' ' + units[i];
    },
    removeFile(index) {
      this.files.splice(index, 1);
    },
    clear(){
      this.reciever = '';
      this.subject = '';
      this.messageBody = '';
      this.files=[];
      this.formData=null;
    },
  },
}
  </script>
<style scoped>
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  background-color: #ccc;
  height: 100px;
  resize: none; 
  line-height: 1.5;
  overflow-y: auto; /* Enable vertical scrolling when content exceeds textarea height */
}
#message-body{
    height:200px;
}
.modal2 {
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 900px;
  height: 200 ;
  padding: 20px;
  background-color: rgb(31, 108, 77);
  border: 2px solid #ccc;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  transition: opacity 0.3s ease;
}
.reciever-input,
.subject-input,
.body-input {
  margin-bottom: 10px;
}
input[type="to"],
input[type="subject"]{
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  background-color: #ccc;
}
button {
    border: 2px solid #ccc;
    border-radius: 5px;  
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    color: rgb(0, 0, 0);
    background-color: transparent;
    width: 20%;
    margin:5px 10px ;
}
.side-btn {
    border: 2px solid #ccc;
    border-radius: 10px;  
    padding: 5px 16px ;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    color: rgb(0, 0, 0);
    background-color: transparent;
    margin: 7px 0px;
    width: 100%;
}
.side-btn:hover {
    background-color: rgb(31, 108, 77);
    box-shadow: 0 0 5px  rgb(31, 108, 77);
    color: #ccc;    
}
button:hover {
  background-color: rgb(24, 86, 63);
}
.cbtn:hover{
 background-color: white;
 font-weight: bold;
}
.sbtn:hover{
 background-color: white;
 font-weight: bold;
}
.sbtn{
    position: absolute;
    right: 0;
    margin-right: 25px;
}
.attachBtn{
  position: absolute;
  width: 20px;
  height: 20px;
  cursor: pointer;
  right: 0;
  margin-right: 25%;
  margin-top: 1%;
}
.p{
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  height: 30px;
  border-radius: 10px;
  border-bottom: 20px;
}
.rate-container {
  display: inline-block;
  margin-right: 20px;
}

.rate-container select {
  padding: 5px;
  font-size: 18px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color:#1f6c4d ;
  color: white;
  cursor: pointer;
}
.attachments{
  width:100%;
  height: 300px;
  overflow-y: auto;
}
</style>
