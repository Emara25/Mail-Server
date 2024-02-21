<template>
    <div class="searchcontainer">
      <input
        type="text"
        :placeholder="'Enter a text'"
        v-model="searchKey"
        @keypress="k => { if (k.key == 'Enter') search() }"
      />
      <select v-model="filter" id="filter">
        <option value="" disabled>FilterBy</option>
        <option v-for="option in filterOptions" :key="option.value" :value="option.value">
          {{ option.label }}
        </option>
      </select>
      <select v-model="sort" id="sort">
        <option value="" disabled>SortBy</option>
        <option v-for="option in sortOptions" :key="option.value" :value="option.value">
          {{ option.label }}
        </option>
      </select>

      <select v-model="sortType" id="sortType">
        <option v-for="option in TypeOptions" :key="option.value" :value="option.value">
          {{ option.label }}
        </option>
      </select>

      <button class="btn" @click="search()">Search</button>
      <button class="btn2" @click="signOut()">SignOut</button>
    </div>
  </template>

  <script>
  export default {
    name: 'SearchBar',
    data() {
      return {
        searchKey: '',
        sort:'',
        filter:'',
        filterBy: 'subject',
        sortBy:'date',
        sortType:'descending',
  
        filterOptions: [
          { value: 'subject', label: 'subject' },
          { value: 'sender', label: 'sender' },
        ],
        sortOptions: [
          { value: 'date', label: 'date' },
          { value: 'priority', label: 'priority' },
        ],
        TypeOptions: [
          { value: 'descending', label: 'descending' },
          { value: 'ascending', label: 'ascending' },
        ],
      };
    },
    watch: {
      filter(newFilter) {
        this.filterBy = newFilter;
        this.Myfilter();
      },
      sort(newSort,oldSort) {
        if(newSort !== oldSort){
        this.sortBy = newSort;
        this.Mysort();
        }
      },
      sortType(newSortType,oldSortType){
        if(newSortType !== oldSortType){
        this.sortType = newSortType;
        this.Mysorttype();
        }
      },
    },
    methods: {

      search() {
          if(this.searchKey!="")
              this.$emit("sendChange",1,this.searchKey, this.filterBy);
      },
      Myfilter(){
        this.$emit("sendChange",2,this.searchKey, this.filterBy);
      },
      Mysort(){
        this.$emit("sendChange",3,this.sortBy, this.sortType);
      },
      Mysorttype(){
        this.$emit("sendChange",4,this.sortBy, this.sortType);
      },
      signOut(){
        this.$router.push("/");
      }
    },
  };
  </script>

<style scoped>
select{
    color: #1d2129;
    font-size: 18px;
    margin: 0px 5px;
    width: 10%;
    border: 1px solid #ccc;
    border-radius: 10px;
    border-style: groove;
    background-color: rgb(232, 192, 49);

}
select:focus {
    border: 2px solid  rgb(31, 108, 77); 
    outline: none; 
    box-shadow: 0 0 5px  rgb(31, 108, 77);
}
.searchcontainer {
        display: flex;
        justify-content: space-between;
        align-self: center;
        margin: 30px ;
    }

    .searchcontainer input {
        width: 70%;
        padding: 5px;
        margin: 0px 5px;
        font-size: larger;
        border-radius: 10px;
        border-style: groove;
    }

    .searchcontainer .btn {
        font-size: 18px;
        background-color: rgb(232, 192, 49);
        border-radius: 10px;
        border-style: groove;
        margin: 0px 5px;
        width: 15%;
    }

    .searchcontainer button:hover {
        background-color: rgb(31, 108, 77);
    }
    .btn2{
        font-size: 16px;
        background-color: rgb(232, 192, 49);
    }
    </style>