<template>
    <div class="sidebar">
        <img src="hogwarts.png" style="width: 180px; height: 190px; cursor: pointer ; display:grid; place-items: center;">
        <div class= "menu-items">
                <MyMessage ref="modal2"/>
                <MyModal :folders="createdFolders" ref="modal" @sendFolder="createFolder"/>
                <br><br>
                <MyContacts :contacts="contacts" @updateContacts="updateContacts"/>
                <button v-for="sideElement in sidebar" 
                    :key="sideElement.value" 
                    @click="sendButton(sideElement.value)" 
                    class="side-btn">
                        <div class="link-container">
                            {{sideElement.value}}
                        </div>
                </button>
                <div class="f-container">
                <div class="c1">
                <button v-for="newFolder in createdFolders" 
                    :key="newFolder.value" 
                    @click="sendButton(newFolder.value)" 
                    class="side-btn2" id="createdF">
                    {{newFolder.value}}   
                </button>
                </div>
                <div class="c2">
                <img v-for="newFolder in createdFolders" :key="newFolder.value"   @click.stop="deleteF(newFolder)" 
                    alt="delete" 
                    src="delete.png" 
                    class="imgButton" >
                </div>
                <div class="c3">
                <img v-for="newFolder in createdFolders" :key="newFolder.value"   @click.stop="editF(newFolder)" 
                    alt="rename" 
                    src="rename.png" 
                    class="imgButton" >
                </div>
                </div>
                <MyModal2 v-if="editing" :folders="createdFolders" @editFolder="editFolder" @closeFolder="closeEdit"/>
        </div>
    </div>
</template>

<script>
import MyModal from './MyModal.vue';
import MyMessage from './MyMessage.vue';
import MyContacts from './MyContacts.vue';
import MyModal2 from './MyModal2.vue';
export default {
    name:'SideBar',
    components: {
        MyModal,
        MyMessage,
        MyContacts,
        MyModal2,
    },
    props:{
        createdFolders:[],
    },
    data(){
        return{
            editing:false,
            editedFolder:{},
            contacts: [
                { name: "mohamed", emails: ["mohamed@hogwarts.com"] },
                { name: "ahmed", emails: ["ahmed@hogwarts.com", "ahmed2@example.com"] },
            ],
            currentFolder:null,
            sidebar:[
                {value: 'inbox'},
                {value: 'sent'},
                {value: 'draft'},
                {value: 'trash'},
            ],
        }
    },
    methods: {
        updateContacts(newContacts) {
        this.contacts = newContacts;
        },
        sendButton(value){
            this.$emit("sendButton", value); 
        },
        createFolder(folders){ 
            this.$emit('updateCopyOptions',folders);
        },
        deleteF(folderToDelete){
            // this.$nextTick(() => {
            //     this.$refs.modal.delete(folderToDelete);
            // });
            this.$emit("deleteFolder",folderToDelete);
            
        },
        editF(folderToEdit){
            this.editing=true;
            this.editedFolder=folderToEdit;
        },
        editFolder(folderAfterEdit){
            this.$emit("editFolder",this.editedFolder,folderAfterEdit);
        },
        closeEdit(){
            this.editing = false;
        }
    }
}
</script>

<style scoped>
.title {
    color: rgb(0, 0, 0);
    font-size: 24px;
    margin-top: 30px;
    margin-bottom: 30px;
    margin-left: 10px;
}
.sidebar {
    width: 190px;
      overflow-y: auto; 
      scrollbar-color: rgb(31, 108, 77) #f0f0f0;
    }
    .sidebar::-webkit-scrollbar {
      width: 7px;
    }

    .sidebar::-webkit-scrollbar-thumb {
      background-color: rgb(31, 108, 77);
    }

    .sidebar::-webkit-scrollbar-track {
      background-color: #f0f0f0;
    }
.menu-items {
    
    margin-top: 20px;
    margin-left: 6px;
    margin-right: 6px;
}

.menu-items > * {
    margin-top: 60px;
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
.side-btn:focus {
    outline: none;
}
.side-btn:hover {
    background-color: rgb(31, 108, 77);
    box-shadow: 0 0 5px  rgb(31, 108, 77);
    color: #ccc;  
    font-weight: bold;  
}
.side-btn.active {
    position: relative;
    background-color: rgb(31, 108, 77);
    color: teal;
    font-weight: 600;
    
}
.custom-side-btn-container {
  position: relative;
  margin-bottom: 10px; 
}
.f-container {
  display: flex;
}

.c1,
.c2,
.c3 {
  flex: 1;
}
.c2,
.c3{
    display: grid;
    margin-top: 15px;
}

.side-btn2 {
    display: grid;
    justify-content: center;
    border: 2px solid #ccc;
    border-radius: 10px;  
    padding: 5px 16px ;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    color: rgb(0, 0, 0);
    background-color: transparent;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin: 7px 0px;
    width: 90px;
    height:40px;
}

.imgButton {
  width: 20px;
  height: 20px;
  cursor: pointer;
  margin-left: 7px;
}

.side-btn2:hover {
  background-color: rgb(31, 108, 77);
  box-shadow: 0 0 5px rgb(31, 108, 77);
  color: #ccc;
  font-weight: bold;
}

#createdF{
    display: flex;
      align-items: center;
}
</style>

