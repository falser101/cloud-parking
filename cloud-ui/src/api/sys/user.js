import request from '@/utils/request'

export function userList(data) {
  return request({
    url: '/user/api/user',
    method: 'get',
    params: data
  })
}

export function userDetail(id) {
  return request({
    url: '/user/api/user/' + id,
    method: 'get'
  })
}

export function delUser(data) {
  return request({
    url: '/user/api/user/' + data,
    method: 'delete'
  })
}

export function addUser(data) {
  return request({
    url: '/user/api/user',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/api/user',
    method: 'put',
    data
  })
}

