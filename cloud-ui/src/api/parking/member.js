import request from '@/utils/request'

export function memberList(data) {
  return request({
    url: '/parking/api/parking/member',
    method: 'get',
    params: data
  })
}

export function addMember(data) {
  return request({
    url: '/parking/api/parking/member',
    method: 'post',
    data
  })
}

export function updateMember(data) {
  return request({
    url: '/parking/api/parking/member',
    method: 'put',
    data
  })
}

export function memberInfo(id) {
  return request({
    url: '/parking/api/parking/member/' + id,
    method: 'get'
  })
}
