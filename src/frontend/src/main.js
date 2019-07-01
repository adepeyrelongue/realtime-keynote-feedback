// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueGoogleCharts from 'vue-google-charts'
// Components
import './components'

// Plugins
import './plugins'

// Sync router with store

// Application imports
import App from './App'
import i18n from '@/i18n'
import router from '@/router'
Vue.use(VueGoogleCharts)

// Sync store with router

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  i18n,
  router,
  render: h => h(App)
}).$mount('#app')
