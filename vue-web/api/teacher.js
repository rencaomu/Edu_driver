import request from '@/utils/request'
export default {
  // 分页讲师查询
  getTeacherPage(page,limit) {
    return request({
      url: `/service/teacherfront/getTeacherPage/${page}/${limit}`,
      method: 'post'
    })
  },
  // 讲师详情
  getTeacherInfo(id) {
    return request({
      url: `/service/teacherfront/getTeacherFrontInfo/${id}`,
      method: 'get'
    })
  }
}
