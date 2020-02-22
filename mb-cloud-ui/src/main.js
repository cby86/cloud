// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.

import Vue from 'vue'
import App from './App'
import router from './router'
import store from "./store"

import request from "./config/httpAapter"
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import utils from "./config/utils"
import 'default-passive-events'

const bus = new Vue();
Vue.prototype.bus = bus;
Vue.prototype.$request = request;
Vue.prototype.$utils = utils;
Vue.config.productionTip = false
Vue.use(ElementUI)
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
