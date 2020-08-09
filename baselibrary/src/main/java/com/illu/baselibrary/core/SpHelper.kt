@file:Suppress("UNCHECKED_CAST")

package com.illu.baselibrary.core

import android.content.Context
import androidx.core.content.edit
import com.illu.baselibrary.App
import java.lang.IllegalArgumentException

private const val SP_NAME = "sp_name"

@JvmOverloads
fun <T> getSpValue(
    key: String,
    defaultValue: T,
    fileName: String = SP_NAME
): T {
    val sp = App.INSTANCE.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    return when(defaultValue) {
        is Boolean -> sp.getBoolean(key, defaultValue) as T
        is String -> sp.getString(key, defaultValue) as T
        is Int -> sp.getInt(key, defaultValue) as T
        is Float -> sp.getFloat(key, defaultValue) as T
        is Long -> sp.getLong(key, defaultValue) as T
        is Set<*> -> sp.getStringSet(key, defaultValue as Set<String>) as T
        else -> throw IllegalArgumentException("Unrecognized default value $defaultValue")
    }
}

@JvmOverloads
fun <T> putSpValue(
    key: String,
    value: T,
    fileName: String = SP_NAME
) {
    App.INSTANCE.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit {
        when(value) {
            is Boolean -> putBoolean(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is Set<*> -> putStringSet(key, value as Set<String>)
            else -> throw IllegalArgumentException("Unrecognized value $value")
        }
        apply()
    }
}

@JvmOverloads
fun removeSpValue(
    key: String,
    fileName: String = SP_NAME
) {
    App.INSTANCE.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        .edit()
        .remove(key)
        .apply()
}

@JvmOverloads
fun clearSpValue(fileName: String = SP_NAME) {
    App.INSTANCE.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()
}