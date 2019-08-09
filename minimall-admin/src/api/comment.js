import request from '@/utils/request'

export function listComment(query) {
  return request({
    url: '/comment/list',
    method: 'get',
    params: query
  })
}

export function deleteComment(data) {
  return request({
    url: '/comment/delete',
    method: 'post',
    data
  })
}

export function replyComment(data) {
  return request({
    url: '/comment/reply',
    method: 'post',
    data
  })
}
