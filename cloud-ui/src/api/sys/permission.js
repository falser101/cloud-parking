import request from '@/utils/request'

export function permissionList(data) {
  return request({
    url: '/user/api/permission',
    method: 'get',
    params: data
  })
}

export function permissionById(id) {
  return request({
    url: '/user/api/permission/' + id,
    method: 'get'
  })
}

export function updatePermission(data) {
  return request({
    url: '/user/api/permission',
    method: 'put',
    data
  })
}

export function addPermission(data) {
  return request({
    url: '/user/api/permission',
    method: 'post',
    data
  })
}

export function delPermission(data) {
  return request({
    url: '/user/api/permission/' + data,
    method: 'delete'
  })
}

export function getLevel1MenuOrInterface(data) {
  return request({
    url: '/user/api/permission/level1',
    method: 'get',
    params: data
  })
}

