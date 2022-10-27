import request from '@/utils/request'
export default {
  // 分页讲师查询
  getPlayAuth(vid) {
    return request({
      url: `/vod/video/getPlayAuth/${vid}`,
      method: 'get'
    })
  }

}
