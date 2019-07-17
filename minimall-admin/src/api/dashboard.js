import request from '@/utils/request'

export function info(query) {
  return request({
    url: '/dashboard/count',
    method: 'get',
    params: query
  })
}
