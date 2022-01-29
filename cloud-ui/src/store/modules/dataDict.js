import { dictList, getById, saveDict } from '@/api/sys/dataDict'

const state = {}

const mutations = {}

const actions = {
  getDataDictLevel({ commit }, reqForm) {
    return new Promise((resolve, reject) => {
      dictList(reqForm).then(response => {
        resolve(response.data)
      }).catch(err => {
        reject(err)
      })
    })
  },

  saveDataDict({ commit }, form) {
    return new Promise((resolve, reject) => {
      saveDict(form).then(response => {
        resolve(response.data)
      }).catch(err => {
        reject(err)
      })
    })
  },
  getDataDict({ commit }, id) {
    return new Promise((resolve, reject) => {
      getById(id).then(response => {
        resolve(response.data)
      }).catch(err => {
        reject(err)
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
