import { asyncRoutes, constantRoutes } from '@/router'
import {
  addPermission, delPermission,
  getLevel1MenuOrInterface,
  permissionById,
  permissionList,
  updatePermission
} from '@/api/sys/permission'

/**
 * 使用name来过滤route
 * @param permissions
 * @param route
 */
function hasPermission(permissions, route) {
  if (route.name) {
    return permissions.some(permission => {
      const { routerName } = permission
      return route.name === routerName
    })
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param permissions 来自当前用户的权限列表
 */
export function filterAsyncRoutes(routes, permissions) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(permissions, tmp)) {
      if (tmp.menus) {
        tmp.menus = filterAsyncRoutes(tmp.menus, permissions)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  permissions: [],
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },

}

const actions = {
  generateRoutes({ commit }, permissions) {
    return new Promise(resolve => {
      commit('SET_PERMISSIONS', permissions)
      const accessedRoutes = filterAsyncRoutes(asyncRoutes, permissions)
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  },

  // 获取列表
  getPermissionList({ commit }, reqForm) {
    return new Promise((resolve, reject) => {
      permissionList(reqForm).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取
  getPermissionById({ commit }, id) {
    return new Promise((resolve, reject) => {
      permissionById(id).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 添加
  addPermission({ commit }, permission) {
    return new Promise((resolve, reject) => {
      const { parentId, permissionType, permissionName, icon, perms, method, orderNum, url, remark } = permission
      const form = {
        parentId: parentId === undefined ? 0 : parentId,
        permissionType: permissionType,
        permissionName: permissionName,
        icon: icon,
        perms: perms,
        method: method,
        orderNum: orderNum,
        url: url,
        remark: remark
      }
      addPermission(form).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 修改权限
  updatePermission({ commit }, permission) {
    return new Promise((resolve, reject) => {
      const { id, parentId, permissionType, permissionName, icon, perms, method, orderNum, url, remark } = permission
      const form = {
        id: id,
        parentId: parentId,
        permissionType: permissionType,
        permissionName: permissionName,
        icon: icon,
        perms: perms,
        method: method,
        orderNum: orderNum,
        url: url,
        remark: remark
      }
      updatePermission(form).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 删除
  delPermissionById({ commit }, id) {
    return new Promise((resolve, reject) => {
      delPermission(id).then(response => {
        const { data } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取一级菜单或一级接口菜单
  getLevel1MenuOrInterface({ commit }, permissionType) {
    return new Promise((resolve, reject) => {
      getLevel1MenuOrInterface({ permissionTypeEnum: permissionType }).then(response => {
        const { data } = response
        resolve(data)
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
