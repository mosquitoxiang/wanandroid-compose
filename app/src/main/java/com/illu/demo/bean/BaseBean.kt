package com.illu.demo.bean

data class BaseBean<T>(
    private val code: Int,
    private val msg: String,
    private val data: T
)