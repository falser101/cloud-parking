import { addUser, delUser, updateUser, userDetail, userList } from '@/api/sys/user'

const getDefaultState = () => {
}

const state = getDefaultState()

const mutations = {}

const actions = {
  userList({ commit }, data) {
    return new Promise((resolve, reject) => {
      const form = {
        loginName: data.loginName !== '' ? data.loginName : undefined,
        userName: data.userName !== '' ? data.userName : undefined,
        mobile: data.mobile !== '' ? data.mobile : undefined,
        size: data.size,
        current: data.current
      }
      userList(form).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  userDetail({ commit }, id) {
    return new Promise((resolve, reject) => {
      userDetail(id).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  delUser({ commit }, id) {
    return new Promise((resolve, reject) => {
      delUser(id).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  addUser({ commit }, form) {
    return new Promise((resolve, reject) => {
      addUser(form).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  updateUser({ commit }, form) {
    return new Promise((resolve, reject) => {
      updateUser(form).then(response => {
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

