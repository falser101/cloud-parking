/**
 * v-hasPermi 操作权限处理
 * Copyright (c) 2019 ruoyi
 */

import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    debugger
    const {value} = binding
    const all_permission = "*:*:*";
    const permissions = store.getters && store.getters.permissions
    if (value && value instanceof Array && value.length > 0) {
      const permissionFlag = value

      const hasPermissions = permissions.some(permission => {
        return all_permission === permission || (permissionFlag.some(perms => {
          let perm = perms.split(':')
          const menus = permission.menus;
          return permission.routerName = perm[0]
            && menus.some(menu => {
              return menu.routerName === perm[1] &&
                menu.buttons.some(button => button.perms === perm[2])
            })
        }))
      })

      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`请设置操作权限标签值`)
    }
  }
}
