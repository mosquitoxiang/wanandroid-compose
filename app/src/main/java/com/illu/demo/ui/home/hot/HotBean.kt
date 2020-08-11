package com.illu.demo.ui.home.hot

data class HotBean(
    var canEdit: Boolean = false,
    var chapterId: String? = "",
    var collect: Boolean = false,
    var courseId: Int = 0,
    var fresh: Boolean = false,
    var id: String? = "",
    var link: String? = "",
    var niceDate: String? = "",
    var niceShareDate: String? = "",
    var publishTime: String? = "",
    var realSuperChapterId: Int = 0,
    var superChapterName: String? = "",
    var title: String? = "",
    var userId: Int = 0,
    var zan: Int = 0
)