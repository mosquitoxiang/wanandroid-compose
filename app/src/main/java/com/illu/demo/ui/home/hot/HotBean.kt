package com.illu.demo.ui.home.hot

import com.illu.demo.bean.Tag

data class HotBean(
    var top: Boolean = false,
    var canEdit: Boolean = false,
    var chapterId: String? = "",
    var collect: Boolean = false,
    var courseId: Int = 0,
    var fresh: Boolean = false,
    var id: Int = 0,
    var link: String? = "",
    var niceDate: String? = "",
    var niceShareDate: String? = "",
    var publishTime: String? = "",
    var realSuperChapterId: Int = 0,
    var superChapterName: String? = "",
    var title: String? = "",
    var desc: String? = "",
    var userId: Int = 0,
    var zan: Int = 0,
    var author: String? = "",

    var chapterName: String? = "",
    var shareUser: String? = "",
    var visible: Int = 0,
    var type: Int = 0,
    var tags: List<Tag> = emptyList()
)