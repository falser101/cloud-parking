import request from '@/utils/request'

export function roleList(data) {
  return request({
    url: '/user/api/sys/role',
    method: 'get',
    params: data
  })
}

export function roleById(id) {
  return request({
    url: '/user/api/sys/role/' + id,
    method: 'get'
  })
}

export function updateRole(data) {
  return request({
    url: '/user/api/sys/role',
    method: 'put',
    data
  })
}

export function getPermissionByRoleId(id) {
  return request({
    url: '/user/api/sys/role/permission/' + id,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/user/api/sys/role',
    method: 'post',
    data
  })
}

export function delRole(data) {
  return request({
    url: '/user/api/sys/role/' + data,
    method: 'delete'
  })
}
