import request from '@/utils/request'

export function carIn(data) {
  return request({
    url: '/parking/api/parking/access/in',
    method: 'post',
    params: data
  })
}

export function carOut(data) {
  return request({
    url: '/parking/api/parking/access/out',
    method: 'post',
    params: data
  })
}

export function imgScan(data) {
  return request({
    url: '/parking/api/parking/access/identify',
    method: 'get',
    params: data
  })
}



