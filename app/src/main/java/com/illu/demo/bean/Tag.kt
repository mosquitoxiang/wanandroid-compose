package com.illu.demo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    var articleId: Long,
    var name: String?,
    var url: String?
) : Parcelable