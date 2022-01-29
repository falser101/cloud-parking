import request from '@/utils/request'

export function orderList(data) {
  return request({
    url: '/parking/api/parking/order',
    method: 'get',
    params: data
  })
}

export function orderInfo(id) {
  return request({
    url: '/parking/api/parking/order/' + id,
    method: 'get'
  })
}
