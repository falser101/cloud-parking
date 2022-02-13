import request from '@/utils/request'

/** 多级数据字典列表*/
export function dictList(data) {
  return request({
    url: '/user/api/data-dict',
    method: 'get',
    params: data
  })
}

/** 多级数据字典列表*/
export function saveDict(data) {
  return request({
    url: '/user/api/data-dict',
    method: 'post',
    data
  })
}

/** 多级数据字典列表*/
export function updateDict(data) {
  return request({
    url: '/user/api/data-dict',
    method: 'put',
    data
  })
}

/**
 * 查询详情
 * @param id
 * @returns
 */
export function getById(id) {
  return request({
    url: '/user/api/data-dict/' + id,
    method: 'get'
  })
}
