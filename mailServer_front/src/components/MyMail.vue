<template>
  <div class="dashboard">
    <Sidebar :createdFolders="dropdownItems" @sendButton="handleButtonClick" @updateCopyOptions="updateDropdownItems" @deleteFolder="deleteFolder" @editFolder="editFolder"/>
    <div class="content">
      <Searchbar @sendChange="handleSearchBar"/>
        <h1>{{this.curFolder}} Messages</h1>
        <div v-if="checkedMessages.length > 0">
          <div class="dropdown" id="BulkCopy" @mouseleave="closeDropdown">
            <button  class="pagBtn" @click="openDropdown">bulkCopy</button>
            
              <div v-if="isDropdownOpen" class="dropdown-content">
                <ul class="dropdown-list">
                  <li v-for="item in dropdownItems" :key="item.value" @click="bulkCopy(item)">
                    {{ item.value }}
                  </li>
                </ul>
              </div>
            </div>
          <button class="pagBtn" @click="bulkDelete()">bulkDelete</button>
        </div>
        <div class="messages">
          <div v-if="isShowing" >
          <div v-for="(message, index) in paginatedItems" :key="index" class="message">
              <div class="rate-container">
                <select v-model="message.priority" @change="handlePriorityChange(message)">
                  <option v-for="priority in [0,1,2,3,4]" :key="priority" :value="priority">{{ priority }}</option>
                </select>
              </div>

            <div v-for="idx in message.priority" :key="idx" class="star-container" style="display: inline-block;"> 
              <img src="star.png" style="height: 25px; width:25px;">
            </div>
          
            <div><strong>From:</strong> {{ message.from }}</div>
            <div><strong>Subject:</strong> {{ message.subject }}</div>

            <img src="delete.png" class="dbtn" @click="deleteMessage(message)">
            <img src="open.png" class="openbtn" @click="openMessage(message)">


            <div class="dropdown" @mouseleave="closeDropdown">
              <img src="copy.png" class="copybtn" @click="openDropdown" >

              <div v-if="isDropdownOpen" class="dropdown-content">
                <ul class="dropdown-list">
                  <li v-for="item in dropdownItems" :key="item.value" @click="copyMessages(message, item)">
                    {{ item.value }}
                  </li>
                </ul>
              </div>
            </div>

            <input type="checkbox" :checked="isChecked(message)" @change="handleCheckboxChange(message)" />
            
            <OpenMessage v-if="open" :message="this.selectedMessage" @closeMessage="this.open=false" />
          </div>
                <button class="pagBtn" @click="previousPage" :disabled="currentPage === 1">&lt;&lt;&nbsp;Previous</button>
                <div class="pageNum">{{ currentPage }}</div >
                <button class="pagBtn" @click="nextPage" :disabled="currentPage===totalPages">Next	&nbsp;&gt;&gt;</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from '../components/SideBar'
import Searchbar from '../components/SearchBar'
import OpenMessage from '../components/OpenMessage'
import axios from 'axios'
export default {
  name: 'MyMail',
  components: {
      Sidebar,
      Searchbar,
      OpenMessage,
  },
  data(){
    return{
      filterBy:'subject',
      sortBy:'date',
      sortType:'descending',
      searchKey:'',
      curEmail:'',
      itemsPerPage: 5,
      currentPage: 1,
      isLastPage:false,
      curFolder:'inbox',
      isShowing: true,
      open: false,
      selectedMessage:{},
      checkedMessages:[],
      is_Checked:false,
      isDropdownOpen: false,
      dropdownItems: [
        { id: 1, value: 'Folder_1' },
        { id: 2, value: 'Folder_2' },
        { id: 3, value: 'Folder_3' },
      ],
      curMessages:[/* {
        priority:3,
        id:1,
        date:"2023-12-23 15:12:45",
        to: 'harry@hogwarts.com',
        from: 'hermione@hogwarts.com',
        subject: 'Magic Lessons',
        body: 'Dear Harry, I hope this owl finds you well. Would you be interested in joining me for some extra magic lessons after classes? Best, Hermione',
        attachments:[{name: "OOP_Assignment_4_Fall_2023(Edit).pdf",size: 130459,type: "application/pdf",webkitRelativePath: ""},
        {name: "Interstaller edit.mp4" ,size: 4295930 ,type: "video/mp4" ,webkitRelativePath: ""},
        {name: "Interstaller edit.mp4" ,size: 4295930 ,type: "video/mp4" ,webkitRelativePath: ""},],
        formData:null,
      },
      { 
        priority:4,
        id:2,
        date:"2023-07-14 08:32:19",
        to: 'ron@hogwarts.com',
        from: 'hagrid@hogwarts.com',
        subject: 'Care of Magical Creatures',
        body: 'Hello Ron, I thought you might enjoy helping me with the magical creatures this weekend. We have some fascinating new arrivals. Interested? Regards, Hagrid',
        attachments:[],
        formData:null,
      },
      {
        priority:0,
        id:3,
        date:"2023-09-06 12:49:10",
        to: 'ginny@hogwarts.com',
        from: 'dumbledore@hogwarts.com',
        subject: 'Important Announcement',
        body: 'Dear Miss Weasley, Please come to my office at your earliest convenience. I have an important announcement to discuss. Sincerely, Professor Dumbledore',
        attachments:[{name: "Interstaller edit.mp4" ,size: 4295930 ,type: "video/mp4" ,webkitRelativePath: ""},],
        formData:null,
      },
      {
        priority:1,
        id:4,
        date:"2023-01-29 06:08:54",
        to: 'neville@hogwarts.com',
        from: 'mcgonagall@hogwarts.com',
        subject: 'Gryffindor House Meeting',
        body: 'Dear Mr. Longbottom, We will be having a Gryffindor House meeting this evening. Your presence is required. Professor McGonagall', 
        attachments:[],
        formData:null,
      },
      {
        priority:2,
        id:5,
        date:"2023-07-15 08:32:19",
        to: 'luna@hogwarts.com',
        from: 'flitwick@hogwarts.com',
        subject: 'Charm Class Update',
        body: 'Hello Luna, I wanted to inform you about some changes to this week\'s Charms class. Please stop by my office for more details.Professor Flitwick ',
        attachments:[],
        formData:null,
      }, 
      {
        priority:1,
        id:6,
        date:"2023-07-7 12:12:12",
        to: 'luna@hogwarts.com',
        from: 'harry@hogwarts.com',
        subject: 'our little secret',
        body: 'Hello Luna, I wanted to inform you about some changes to this week\'s Charms class. Please stop by my office for more details.Professor Flitwick ',
        attachments:[],
        formData:null,
      }, 
    */],
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.curMessages.length / this.itemsPerPage);
    },
    paginatedItems() {
      const startIndex = (this.currentPage - 1) * this.itemsPerPage;
      const endIndex = startIndex + this.itemsPerPage;
      return this.curMessages.slice(startIndex, endIndex);
    },
  },
  mounted(){
    this.curEmail = this.$store.state.email;
    this.dropdownItems = this.$store.state.files;
    console.log(this.curEmail);
    console.log(this.dropdownItems);
    const url = `http://localhost:8080/email`;
          axios.get(url,{
            params:{
              type: this.curFolder,
              user: this.curEmail,
            }
          }).then(response => {
            this.curMessages=response.data;
            this.isShowing = true;
          }).catch(error => {
            alert(error);
            });
          this.isShowing=true;
      },
  methods: {
    handlePriorityChange(message) {
  const url = `http://localhost:8080/email/changePriority`;
  const payload = {
    file: this.curFolder,
    email: this.curEmail,
    id: message.id,
    priority: message.priority,
  };
  console.log('Request Payload:', payload);

  axios.put(url, payload)
    .then(response => {
      this.curMessages = response.data;
      console.log('Priority changed successfully:', response.data);
    })
    .catch(error => {
      console.error('Error:', error);
      alert('Error changing priority. Please check the console for details.');
    });
},
    handleButtonClick(folder){
          this.curFolder = folder;
          this.currentPage=1;
          console.log(this.curEmail);
          const url = `http://localhost:8080/email`;
          axios.get(url,{
            params:{
              type: this.curFolder,
              user: this.curEmail,
            }
          }).then(response => {
            this.curMessages=response.data;
            this.isShowing = true;
          }).catch(error => {
            alert(error);
            });
          this.isShowing=true;
      },
      handleSearchBar(type,value1, value2){
        if(type==1){
          this.searchKey = value1;
          const url = `http://localhost:8080/email/search`;
          axios.get(url,{
            params:{
              file:this.curFolder,
              criteria:this.searchKey,
              email:this.curEmail,
            }
          }).then(response => {
            this.curMessages=response.data;
            this.isShowing = true;
          }).catch(error => {
            alert(error);
            });
          alert("Searching By "+ this.searchKey);
        }
        else if(type==2){
          this.filterBy = value2;
          this.searchKey = value1;
          const url = `http://localhost:8080/email/filter`;
          axios.get(url,{
            params:{
              file:this.curFolder,
              choice:this.filterBy,
              email:this.curEmail,
              criteria: this.searchKey,

            }
          }).then(response => {
            this.curMessages=response.data;
            this.isShowing = true;
          }).catch(error => {
            alert(error);
            });
            alert("filtering by "+this.filterBy);
        }
        else {
          this.sortBy = value1;
          this.sortType = value2;
          if(this.sortBy == "priority"){
              const url = `http://localhost:8080/email/sortByPrio`;
              axios.get(url,{
                params:{
                  file:this.curFolder,
                  email:this.curEmail,
                  sortType:this.sortType
                }
              }).then(response => {
                this.curMessages=response.data;
                this.isShowing = true;
              }).catch(error => {
                alert(error);
                });
              alert("sorting "+this.sortType+" by "+this.sortBy);
        }
        else if(this.sortBy == "date"){
              const url = `http://localhost:8080/email/sortByDate`;
              axios.get(url,{
                params:{
                  file:this.curFolder,
                  email:this.curEmail,
                  sortType:this.sortType
                }
              }).then(response => {
                this.curMessages=response.data;
                this.isShowing = true;
              }).catch(error => {
                alert(error);
                });
              alert("sorting "+this.sortType+" by "+this.sortBy);
        }
      } 
      },
      showMessages(){
        this.isShowing=true;
      },
      async deleteMessage(message) {
  try {
    const response = await axios.delete(`http://localhost:8080/email`, {
      params: {
        user: this.curEmail,
        id: message.id,
        file: this.curFolder,
      },
    });

    console.log('Message deleted successfully:', response.data);
  } catch (error) {
    alert(error);
  }

  const idx = this.curMessages.findIndex((m) => m.id === message.id);
  this.curMessages.splice(idx, 1);
},
      openMessage(message) {
        this.selectedMessage = message;
        this.open = true;
      },
      previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    copyMessages(message, destination) {
  const url = 'http://localhost:8080/email';
  axios.put(url, {
    user: this.curEmail,
    source: this.curFolder,
    id: message.id,
    file: destination.value,
  })
  .then(response => {
    console.log('message copied successfully:', response.data);
  })
  .catch(error => {
    alert(error);
  });
  alert(`Copy message with subject #${message.id}# to #${destination.value}#`);
},
    nextPage() {
      if(this.currentPage+1 <= this.totalPages)
        this.currentPage++;
    },
    openDropdown() {
      this.isDropdownOpen = true;
    },
    closeDropdown() {
      this.isDropdownOpen = false;
    },
    updateDropdownItems(folderName) {
  this.dropdownItems.push(folderName);
  const name = folderName.value;
  axios.post('http://localhost:8080/file', {
    email: this.curEmail,
    name: name,
  })
  .then(response => {
    console.log('folder created successfully:', response.data);
  })
  .catch(error => {
    alert(error);
  });
},
      deleteFolder(folderToDelete){
        const name= folderToDelete.value ;
        axios.delete(`http://localhost:8080/file`,{
          params:{
            email:this.curEmail,
            name:name,
          }
          }).then(response => {
          console.log('folder deleted successfully:', response.data);
          })
          .catch(error => {
            alert(error);
          });
        var index = this.dropdownItems.findIndex(f => f==folderToDelete);
            if (index !== -1) {
                this.dropdownItems.splice(index, 1);
            } else {
                alert(`Folder "${folderToDelete}" not found.`);
            }

      },
      editFolder(oldFolder,newFolder){
        var index = this.dropdownItems.findIndex(f => f==oldFolder);
        this.dropdownItems[index] = newFolder;
        axios.put('http://localhost:8080/file', {
        email: this.curEmail,
        oldName: oldFolder.value,
        newName: newFolder.value,
      })
      .then(response => {
        console.log('folder edited successfully:', response.data);
      })
      .catch(error => {
        alert(error);
      });
      },
    isChecked(message) {
      return this.checkedMessages.includes(message);
    },
    handleCheckboxChange(message) {
      const index = this.checkedMessages.indexOf(message);
      if (index === -1) {
        this.checkedMessages.push(message);
      } else {
        this.checkedMessages.splice(index, 1);
      }
      console.log(this.checkedMessages);
    },
    bulkCopy(destenation){
      this.checkedMessages.forEach(message => {
        this.copyMessages(message, destenation);
      });
      alert(`send this.checkedList to ${destenation}`);
      this.checkedMessages =[];
    },
    async bulkDelete() {
  const messagesToDelete = [...this.checkedMessages]; // Create a copy of the array

  for (const message of messagesToDelete) {
    console.log(message);
    await this.deleteMessage(message);
  }

  // Send messages to trash or perform other actions
  console.log(`Send messages to trash and delete them`);

  this.checkedMessages = []; // Clear the original array
}
  }
};
</script>

<style scoped>
.dashboard {
  display: grid;
  grid-template-columns: 0.5fr 5fr;
  background-color:rgb(232, 192, 49);
  height: 98vh;
  width: 100%;
}

.content {
  background-color: #5f0101;
  border-radius: 10px;
  margin: 6px 6px 6px 0px;
  position: relative;
}
.messages{
  width: 90%;
  height: 70%;
  position: absolute;
  left: 3%;
  border: 2px solid #000; 
  border-radius: 15px;
  padding: 20px;
  background-color:#1f6c4d;
  overflow-y: auto;
}
.message {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #ccc;
}

.message div {
  margin-bottom: 10px;
}

.message strong {
  color: #333;
}
h1{
  color: rgb(108, 141, 189);
  display: grid;
  place-content: center;

}
.dbtn{
    width: 20px;
    height: 20px; 
    position: absolute;
    right:0 ;
    cursor: pointer;
    margin-right: 50px;
}
.openbtn{
    width: 20px;
    height: 20px;
    position: relative;
    cursor: pointer;
}
.copybtn{
    width: 25px;
    height: 25px;
    position: relative;
    cursor: pointer;
    margin-left: 25px;
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

.pagBtn, #BulkCopy{
  position: relative;
  border: 1px solid #ccc;
  border-radius: 10px;
    width: 8%;
    cursor: pointer;
    margin-left: 25px;
    font-weight: bold;
}
.pagBtn, #BulkCopy,.dropdown-content:hover{
  background-color:rgb(232, 192, 49);
}
#BulkCopy{
  left:2%;
  margin-right: 2%;
}
.pageNum{
  display: inline-block;
  width: 3%;
  margin-left:25px; 
  border: 2px solid #ccc;
  border-radius: 20px;
  vertical-align: middle; 
  border-radius: 2px;
  background: #d5fed9 ;
  text-align: center;
  font-weight: bold;
  box-shadow: 5px 5px 5px  rgb(0, 0, 0);
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  z-index: 1;
  width: 100px; 
  max-height: 150px; 
  overflow-y: auto;
  scrollbar-width: thin;
  scrollbar-color: #333 #f0f0f0;
}
.dropdown-content::-webkit-scrollbar {
width: 1px;
}

.dropdown-content::-webkit-scrollbar-thumb {
background-color: #333;
}

.dropdown-content::-webkit-scrollbar-track {
background-color: #f0f0f0;
}
.dropdown:hover .dropdown-content {
  display: block;
  overflow-y: auto;
}

.dropdown-list {
  list-style-type: none; 
  padding: 0;
  margin: 0;
}

.dropdown-list li {
  padding: 8px;
  cursor: pointer;
  border-bottom: 1px solid #ddd;
}

.dropdown-list li:last-child {
  border-bottom: none;
}
li:hover{
  color: greenyellow;
}
input[type="checkbox"] {
  width: 20px;
  height: 20px;
  border: 2px solid #333;
  border-radius: 5px;
  margin-right: 5px;
  cursor: pointer;
  outline: none;
  position: absolute;
  right:6% ;
}
</style>
