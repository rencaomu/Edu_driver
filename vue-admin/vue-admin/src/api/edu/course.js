import request from '@/utils/request'
export default {
    //1 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/service/course/addCourseInfo',
            method: 'post',
            data:courseInfo
          })
    },
    //2 查询所有讲师
    getListTeacher() {
        return request({
            url: '/service/teacher/findAll',
            method: 'get'
          })
    },
    //根据课程id查询课程基本信息
    getCourseInfoId(id) {
        return request({
            url: '/service/course/getCourseInfo/'+id,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/service/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
          })
    },
    //课程确认信息显示
    getPublihCourseInfo(id) {
        return request({
            url: '/service/course/getPublishCourseInfo/'+id,
            method: 'get'
          })
    },
    //课程最终发布
    publihCourse(id) {
        return request({
            url: '/service/course/publishCourse/'+id,
            method: 'post'
          })
    },
    //TODO 课程列表
    //课程最终发布
    getListCourse() {
        return request({
            url: '/service/course',
            method: 'get'
          })
    }

}
