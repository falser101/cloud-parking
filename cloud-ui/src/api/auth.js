import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/api/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/user/api/auth/userinfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/api/auth/logout',
    method: 'post'
  })
}
