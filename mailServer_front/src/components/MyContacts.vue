<template>
      <button @click="openContacts()" class="side-btn">Contacts</button>
      <div v-if="open" class="modal3">
        <div class="header">My Contacts</div>
        <br>
        <input type="text" :placeholder="'Enter a text'" v-model="searchKey" @keypress="k => { if (k.key == 'Enter') search() }"/>
        <button class="btn" @click="search()">Search</button>
        <br><br><br><br>
        <div class="contact-list">
          <div v-for="(contact, index) in contacts" :key="index" class="contact">
            <strong>Name:</strong>
            <input type="text" v-model="contact.name" />
            <strong>Emails:</strong>
            <div v-for="(email, emailIndex) in contact.email" :key="emailIndex" class="email-input">
              <input type="text" :value="email" @input="updateEmail(contact, emailIndex, $event.target.value)" />
              <button @click="removeEmail(contact, emailIndex)">Remove</button>
            </div>
            <button @click="addEmail(contact)">Add Email</button>
            <button @click="removeContact(index)">Remove Contact</button>
          </div>
          <button @click="addContact()">Add Contact</button>
          <button class="sbtn" @click="submit()">Submit</button>
          <button class="cbtn" @click="open=false">Close</button>
        </div>
      </div>
  </template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      open: false,
      curEmail: '',
      searchKey: '',
      contacts: [], 
    };
  },
  mounted() {
    this.curEmail = this.$store.state.email;
  },
  methods: {
    openContacts() {
      this.searchKey = '';
      this.open = true;
      const url = `http://localhost:8080/contacts`;
      axios.get(url, {
        params: {
          user: this.curEmail,
        },
      }).then(response => {
        this.contacts = response.data;
        console.log(response.data);
      }).catch(error => {
        alert(error);
      });
    },
    submit() {
      this.open = false;
      console.log(this.contacts);
      let x = this.contacts;
      console.log(x);
      const url = `http://localhost:8080/contacts`;
      axios.post(url, x, {
        params: {
          user: this.curEmail,
        },
      }).then(response => {
        console.log('contacts changed successfully:', response.data);
      }).catch(error => {
        alert(error);
      });
    },
    search() {
      if (this.searchKey !== '') {
        const url = `http://localhost:8080/contacts/search`;
        axios.get(url, {
          params: {
            key: this.searchKey,
            user: this.curEmail,
          },
        }).then(response => {
          this.contacts = response.data;
        }).catch(error => {
          alert(error);
        });
        alert("Searching By " + this.searchKey);
      }
    },
    removeEmail(contact, emailIndex) {
      contact.email.splice(emailIndex, 1);
    },
    addEmail(contact) {
      contact.email.push('');
    },
    removeContact(contactIndex) {
      this.contacts = this.contacts.filter((_, idx) => idx !== contactIndex);
    },
    updateEmail(contact, emailIndex, value) {
      this.contacts = this.contacts.map((c, idx) => {
        if (idx === this.contacts.indexOf(contact)) {
          c.email[emailIndex] = value;
        }
        return c;
      });
    },
    addContact() {
      const newContact = { name: '', email: [''] };
      this.contacts = [...this.contacts, newContact];
    },
  },
};
</script>

  <style scoped>
  .modal3 {
    width: fit-content;
    height: 650px;
    position: fixed;
    z-index: 999;
    top: 50%;
    left: 40%;
    transform: translate(-50%, -50%);
    padding: 20px;
    background-color: rgb(232, 192, 49);
    border: 2px solid #ccc;
    border-radius: 15px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    transition: opacity 0.3s ease;
    
  }
  .contact-list {
  height: 500px; 
  width:fit-content;
  overflow-y: auto;
}

.contact {
  margin-bottom: 20px;
}
  
  .header {
    color: #333;
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 15px;
  }
  
  .contact {
    margin-bottom: 20px;
  }
  
  .contact strong {
    display: block;
    margin-bottom: 5px;
  }
  
  .email-input {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  
  .email-input input {
    flex: 1;
    padding: 5px;
    margin-right: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  .email-input button {
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;
    background-color: transparent;
    padding: 5px;
    color: #333;
    font-size: 14px;
  }
  
  .email-input button:hover {
    background-color: #1f6c4d;
  }
  
  button {
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    font-weight:500;
    color: #333;
    background-color: transparent;
    width: fit-content;
    margin: 5px 10px;
  }
  
  button:hover {
    background-color: #1f6c4d;
    font-weight: bold;
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
.cbtn{
  margin-left: 300px;
}
input {
        width: 70%;
        padding: 5px;
        margin: 0px 5px;
        font-size: larger;
        border-radius: 10px;
        border-style: groove;
    }
    .btn {
        font-size: 18px;
        background-color: rgb(232, 192, 49);
        border-radius: 10px;
        border-style: groove;
        margin: 0px 5px;
        width: 24%;
    }
</style>
