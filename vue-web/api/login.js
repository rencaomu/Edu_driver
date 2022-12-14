import request from '@/utils/request'
export default {
  // 登录
  submitLoginApi(userInfo) {
    return request({
      url: `/educenter/member/login`,
      method: 'post',
      data: userInfo
    })
  },
  // 根据token获取用户信息
  getLoginUserInfo() {
    return request({
      url: `/educenter/member/getUserInfo`,
      method: 'get'
    })
  }
}
