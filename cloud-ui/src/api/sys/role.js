import request from '@/utils/request'

export function roleList(data) {
  return request({
    url: '/user/api/role',
    method: 'get',
    params: data
  })
}

export function roleById(id) {
  return request({
    url: '/user/api/role/' + id,
    method: 'get'
  })
}

export function updateRole(data) {
  return request({
    url: '/user/api/role',
    method: 'put',
    data
  })
}

export function getPermissionByRoleId(id) {
  return request({
    url: '/user/api/role/permission/' + id,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/user/api/role',
    method: 'post',
    data
  })
}

export function delRole(data) {
  return request({
    url: '/user/api/role/' + data,
    method: 'delete'
  })
}
