import { createStore } from 'vuex';

const store = createStore({
  state: {
    email: "",
    files:[],
  },
  mutations: {
    setSharedVariable(state, payload) {
      state.email = payload;
    },
    setFiles(state,payload){
      state.files = payload;
    },
  },
});

export default store;
