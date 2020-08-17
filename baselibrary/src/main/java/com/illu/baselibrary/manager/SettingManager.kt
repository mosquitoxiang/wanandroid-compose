package com.illu.baselibrary.manager

import com.illu.baselibrary.core.getSpValue
import com.illu.baselibrary.core.putSpValue

object SettingManager {

    private const val NIGHT_MODE = "night_mode"

    fun setNightMode(nightMode: Boolean) = putSpValue(NIGHT_MODE, nightMode)
    fun getNightMode() = getSpValue(NIGHT_MODE, false)
}