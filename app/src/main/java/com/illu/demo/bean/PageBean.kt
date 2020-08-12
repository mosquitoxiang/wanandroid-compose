package com.illu.demo.bean

data class PageBean<T> (
    var curPage: Int = 0,
    var datas: List<T>,
    var offset: Int = 0,
    var over: Boolean = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0
)