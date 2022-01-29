import request from '@/utils/request'

export function spaceList(data) {
  return request({
    url: '/parking/api/parking/parking_space',
    method: 'get',
    params: data
  })
}

export function addSpace(data) {
  return request({
    url: '/parking/api/parking/parking_space',
    method: 'post',
    data
  })
}

export function getSpace(id) {
  return request({
    url: '/parking/api/parking/parking_space/' + id,
    method: 'get'
  })
}
