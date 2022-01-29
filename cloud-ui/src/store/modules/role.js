import { addRole, delRole, roleById, roleList, updateRole } from '@/api/sys/role'

const state = {}

const mutations = {}

const actions = {
  // 获取列表
  getRoleList({ commit }, reqForm) {
    return new Promise((resolve, reject) => {
      const form = {
        roleName: reqForm.roleName !== '' ? reqForm.roleName : undefined,
        roleKey: reqForm.roleKey !== '' ? reqForm.roleKey : undefined,
        current: reqForm.current,
        size: reqForm.size
      }
      roleList(form).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取
  getRoleById({ commit }, id) {
    return new Promise((resolve, reject) => {
      roleById(id).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 修改角色
  updateRole({ commit }, role) {
    return new Promise((resolve, reject) => {
      updateRole(role).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 添加角色
  addRole({ commit }, data) {
    return new Promise((resolve, reject) => {
      addRole(data).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  delRole({ commit }, id) {
    return new Promise((resolve, reject) => {
      delRole(id).then(response => {
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
