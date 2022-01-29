import request from '@/utils/request'

export function userList(data) {
  return request({
    url: '/user/api/sys/user',
    method: 'get',
    params: data
  })
}

export function userDetail(id) {
  return request({
    url: '/user/api/sys/user/' + id,
    method: 'get'
  })
}

export function delUser(data) {
  return request({
    url: '/user/api/sys/user/' + data,
    method: 'delete'
  })
}

export function addUser(data) {
  return request({
    url: '/user/api/sys/user',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/api/sys/user',
    method: 'put',
    data
  })
}

