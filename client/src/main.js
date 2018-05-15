import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VueRx from 'vue-rx';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs';
import VueAxios from 'vue-axios';
import Axios from 'axios';
Vue.config.productionTip = false;
Vue.use(VueRx, {
    Observable,
    Subscription
});
Vue.use(VueAxios, Axios);
new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount('#app');
//# sourceMappingURL=main.js.map