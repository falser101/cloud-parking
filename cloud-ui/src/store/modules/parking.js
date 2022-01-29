import {addMember, memberInfo, memberList, updateMember} from "@/api/parking/member";
import {addSpace, getSpace, spaceList} from "@/api/parking/parkingSpace";
import {error} from "autoprefixer/lib/utils";
import {carIn, carOut} from "@/api/parking/access";
import {infoList} from "@/api/parking/accessInfo";


const state = {}

const mutations = {}

const actions = {
  getMemberList({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      memberList(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  addMember({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      addMember(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  updateMember({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      updateMember(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  memberInfo({commit}, id) {
    return new Promise((resolve, reject) => {
      memberInfo(id).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  spaceList({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      spaceList(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  addSpace({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      addSpace(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  getSpace({commit}, id) {
    return new Promise((resolve, reject) => {
      getSpace(id).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  carIn({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      carIn(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  carOut({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      carOut(reqForm).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  accessInfoList({commit}, reqForm) {
    return new Promise((resolve, reject) => {
      infoList(reqForm).then(response => {
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
