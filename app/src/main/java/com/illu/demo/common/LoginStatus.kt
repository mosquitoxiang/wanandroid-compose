package com.illu.demo.common

import com.illu.demo.net.HttpUtils

fun isLogin() = UserManager.getUserInfo() != null && HttpUtils.hasCookie()