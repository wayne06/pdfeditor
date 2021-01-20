import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import vueConfig from '../vue.config.js'

var axios = require('axios')
// axios.defaults.baseURL = 'http://127.0.0.1:8443/api'
// axios.defaults.baseURL = 'http://139.159.183.141:8090/api'
axios.defaults.baseURL = vueConfig.devServer.proxy["/api"].target + '/api';
axios.defaults.withCredentials = true
Vue.prototype.$axios = axios

Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
