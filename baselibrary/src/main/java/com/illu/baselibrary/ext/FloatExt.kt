package com.illu.baselibrary.ext

import com.illu.baselibrary.utils.dpToPx
import com.illu.baselibrary.utils.pxToDp

fun Float.dpToPx() = dpToPx(this)

fun Float.dpToPxInt() = dpToPx(this).toInt()

fun Float.pxToDp() = pxToDp(this)

fun Float.pxToDpInt() = pxToDp(this).toInt()