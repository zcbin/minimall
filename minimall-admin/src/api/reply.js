import request from '@/utils/request'

export function replyComment(data) {
  return request({
    url: '/comment/reply',
    method: 'post',
    data
  })
}
