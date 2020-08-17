package com.illu.demo.ui.mine.rank.minepoints

data class MinePointsListBean(
    var coinCount: Int = 0,
    var date: Long = 0L,
    var desc: String? = "",
    var id: Int = 0,
    var reason: String? = "",
    var type: Int = 0,
    var userId: Int = 0,
    var userName: String = ""
)

data class MinePointsBean(
    var coinCount: Int = 0,
    var rank: Int = 0,
    var level: Int = 0,
    var userId: Int = 0,
    var username: String? = ""
)