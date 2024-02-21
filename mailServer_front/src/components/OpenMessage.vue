<template>
    <div class="modal3" >
      <div class="header">Message Info</div>
      <div class="info">
        <div class="reciever-input">
          <strong>Sender:</strong> {{ message.from }}
        </div>
        <div class="reciever-input">
          <strong>Receiver:</strong> {{ message.to }}
        </div>
        <div class="subject-input">
          <strong>Subject:</strong> {{ message.subject }}
        </div>
        <div class="date-input">
          <strong>Date and Time:</strong> {{ message.date }}
        </div>
        <div class="body-input">
           {{ message.body }}
        </div>
        <div v-if="message.attachments.length > 0" class="attachments">
                <h2>Attachments:</h2>
                <ul>
                  <li v-for="(file, index) in message.attachments" :key="index">
                    <a :href="file.url" :download="file.name">
                      {{ file.name }} ({{ formatFileSize(file.size) }})
                    </a>
                    <button @click="downloadFile(index)">Download</button>
                  </li>
                </ul>
              </div>
      </div>
      <button class="cbtn" @click="close()">Close</button>
    </div>
  </template>

  <script>
  export default {
    props:{
        message: {},
    },
      data() {
        return {
        }
      },
      methods: {
        close(){
            this.$emit("closeMessage");
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
        downloadFile(index) {
          const fileData = this.message.formData.get(`file${index + 1}`);
          if (fileData instanceof File) {
            const blob = new Blob([fileData], { type: fileData.type });
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = fileData.name;
            link.click();
          }
        },
      },
  }
  </script>
  <style scoped>
  .modal3 {
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 900px;
  padding: 20px;
  background-color: rgb(31, 108, 77);
  border: 2px solid #ccc;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  transition: opacity 0.3s ease;
}

.header {
  color: #fff;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 15px;
}

.info {
  color: #fff;
  font-size: 18px;
}

.reciever-input,
.subject-input,
.date-input,
.body-input {
  color:black;
  background-color: white;
  padding: 10px;
  margin-bottom: 15px;
  border-radius: 8px;
}
.body-input {
 height:100px;
 overflow-y: auto;
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
  margin: 5px 10px;
}

button:hover {
  background-color: white;
  font-weight: bold;
}
.attachments{
  width:100%;
  height: 300px;
  overflow-y: auto;
}

</style> 