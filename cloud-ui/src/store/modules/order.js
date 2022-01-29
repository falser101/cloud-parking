import {orderInfo, orderList} from "@/api/parking/order";

const state = {}

const mutations = {}

const actions = {
  orderList({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      orderList(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  orderInfo({commit}, id) {
    return new Promise((resolve, reject) => {
      orderInfo(id).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
