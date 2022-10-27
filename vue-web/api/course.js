import request from '@/utils/request'
export default {
  // 课程条件查询分页
  getCourseList(page,limit,searchobj) {
    return request({
      url: `/service/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: searchobj
    })
  },
  // 查询所有分类
  getAllSubject() {
    return request({
      url: '/service/subject/getAllSubject',
      method: 'get'
    })
  },
  //课程详情的方法
  getCourseInfo(id) {
    return request({
      url: `/service/coursefront/getFrontInfo/${id}`,
      method: 'get'
    })
  }

}
