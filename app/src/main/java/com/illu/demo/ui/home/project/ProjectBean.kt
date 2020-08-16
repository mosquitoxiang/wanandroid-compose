package com.illu.demo.ui.home.project

data class ProjectBean(
    var courseId: Int = 0,
    var id: Int = 0,//根据id查找对应的文章列表
    var name: String? = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0
)