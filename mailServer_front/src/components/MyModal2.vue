<template>
  <Teleport to="body" >
    <transition name="fade">
      <div class="modal" @mousedown="startDrag" v-bind:style="{ top: top + 'px', left: left + 'px' }">
        <div class="p">Rename Folder</div>
        <div class="formElement">
        <input type="text" v-model="folderName.value" 
        id="folder name" placeholder="Enter new name"
        @keypress="k => { if (k.key == 'Enter') handleFormName() }" />
        </div>
        <button class="ocbtn" @click=close()>Close</button>
        <button class="ocbtn" @click="handleFormName()">Submit</button>
      </div>
    </transition>
</Teleport>
</template>

<script>
export default {
    props:{
      folders: [],
    },
    data() {
      return {
       isDragging: false,
       startX: 0,
       startY: 0,
       left: 700,
       top: 300,
       folderName:{value:null},
      };
    },
    methods: {
      clearFormName(){
        this.folderName.value=null;
      },
      checkFolder(){
        return this.folders.find(f => f.value==this.folderName.value);
      },
      handleFormName() {
        if (this.folderName.value === null) {
          alert('Folder name can not be empty!!');
          return;
        }
        else if(this.checkFolder()){
          console.log(this.folders);
          alert("Folder exists");
          return;
        }
        this.sendFolder();
      },
      sendFolder() {
        this.$emit("editFolder",{...this.folderName});
        this.$emit("closeFolder");
      },
      close(){
        this.$emit("closeFolder");
      },
      startDrag(e) {
      this.isDragging = true;
      this.startX = e.clientX - this.left;
      this.startY = e.clientY - this.top;

      document.addEventListener('mousemove', this.drag);
      document.addEventListener('mouseup', this.stopDrag);
    },
    drag(e) {
      if (this.isDragging) {
        this.left = e.clientX - this.startX;
        this.top = e.clientY - this.startY;
      }
    },
    stopDrag() {
      this.isDragging = false;
      document.removeEventListener('mousemove', this.drag);
      document.removeEventListener('mouseup', this.stopDrag);
    },      
    },
    
}
</script>

<style scoped>
.p{
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  display: grid;
  place-content: center;
  height: 30px;
  border-radius: 10px;
  border-bottom: 20px;
}
.ocbtn:hover{
 background-color: white;
 font-weight: bold;
}
.modal {
  position: fixed;
  z-index: 999;
  top: 40%;
  left: 50%;
  width: 300px;
  height: fit-content;
  margin-left: -150px;
  background-color: rgb(31, 108, 77);
  border: 2px solid #ccc;
  border-radius: 15px;
  transition: opacity  0.3s ease;
  cursor:grab;
  
}
button {
    border: 2px solid #ccc;
    border-radius: 5px;  
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    color: rgb(0, 0, 0);
    background-color: transparent;
    width: fit-content;
    margin:5px 10px ;
}
.formElement {
    color: #1d2129;
    font-size: 12px;
    height: 22px;
    line-height: 16px;
    vertical-align: middle;
    border-radius: 4px;
    margin-right: 8px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: rgb(31, 108, 77);
  color: #fff;
}
input{
  width: 100%;
  padding: 2px 9px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 18px;
  background-color: #ccc;
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
.fade-enter-from .fade-container,
.fade-leave-to .fade-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>