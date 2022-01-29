import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import permission from './modules/permission'
import settings from './modules/settings'
import auth from './modules/auth'
import user from './modules/user'
import role from '@/store/modules/role'
import dataDict from '@/store/modules/dataDict'
import parking from '@/store/modules/parking'
import order from '@/store/modules/order'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    permission,
    settings,
    user,
    role,
    auth,
    dataDict,
    parking,
    order
  },
  getters
})

export default store
