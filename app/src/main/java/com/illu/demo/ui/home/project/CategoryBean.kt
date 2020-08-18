package com.illu.demo.ui.home.project

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryBean(
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String? = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0,
    val children: ArrayList<CategoryBean>
): Parcelable