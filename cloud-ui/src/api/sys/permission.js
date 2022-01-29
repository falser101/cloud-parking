import request from '@/utils/request'

export function permissionList(data) {
  return request({
    url: '/user/api/sys/permission',
    method: 'get',
    params: data
  })
}

export function permissionById(id) {
  return request({
    url: '/user/api/sys/permission/' + id,
    method: 'get'
  })
}

export function updatePermission(data) {
  return request({
    url: '/user/api/sys/permission',
    method: 'put',
    data
  })
}

export function addPermission(data) {
  return request({
    url: '/user/api/sys/permission',
    method: 'post',
    data
  })
}

export function delPermission(data) {
  return request({
    url: '/user/api/sys/permission/' + data,
    method: 'delete'
  })
}

export function getLevel1MenuOrInterface(data) {
  return request({
    url: '/user/api/sys/permission/level1',
    method: 'get',
    params: data
  })
}

