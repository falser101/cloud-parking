import request from '@/utils/request'

export function infoList(data) {
  return request({
    url: '/parking/api/parking/access_info',
    method: 'get',
    params: data
  })
}
