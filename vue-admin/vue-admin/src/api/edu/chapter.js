import request from '../../utils/request'
export default {
    //1 根据课程id获取章节和小节数据列表
    getAllChapterVideo(courseId) {
        return request({
            url: '/service/chapter/getChapterVideo/'+courseId,
            method: 'get'
          })
    },
    //添加章节
    addChapter(chapter) {
        return request({
            url: '/service/chapter/addChapter',
            method: 'post',
            data: chapter
          })
    },
    //根据id查询章节
    getChapter(chapterId) {
        return request({
            url: '/service/chapter/getChapterInfo/'+chapterId,
            method: 'get'
          })
    },
    //修改章节
    updateChapter(chapter) {
        return request({
            url: '/service/chapter/updateChapter',
            method: 'post',
            data: chapter
          })
    },
    //删除章节
    deleteChapter(chapterId) {
        return request({
            url: '/service/chapter/'+chapterId,
            method: 'delete'
          })
    },
}
