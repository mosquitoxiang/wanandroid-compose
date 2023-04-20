package com.illu.demo.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleBean(
    var top: Boolean = false,
    var apkLink: String = "",
    var audit: Int = 0,
    var author: String = "",
    var canEdit: Boolean = false,
    var chapterId: String = "",
    var chapterName: String = "",
    var collect: Boolean = false,
    var courseId: Int = 0,
    var desc: String = "",
    var descMd: String = "",
    var envelopePic: String = "",
    var fresh: Boolean = false,
    var id: Int = 0,
    var link: String = "",
    var niceDate: String = "",
    var niceShareDate: String = "",
    var origin: String = "",
    var originId: Int = 0,
    var publishTime: Long = 0L,
    var shareUser: String = "",
    var superChapterId: Int = 0,
    var superChapterName: String = "",
    var tags: List<Tag> = emptyList(),
    var title: String = "",
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0
) : Parcelable