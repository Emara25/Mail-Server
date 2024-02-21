import { createRouter, createWebHashHistory } from 'vue-router';
import SignIn from '@/components/SignIn.vue';
import SignUp from '@/components/SignUp.vue';
import MyMail from '@/components/MyMail.vue';

const routes = [
  { path: '/', component: SignIn },
  { path: '/signup', component: SignUp },
  { path: '/mail', component: MyMail },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;

