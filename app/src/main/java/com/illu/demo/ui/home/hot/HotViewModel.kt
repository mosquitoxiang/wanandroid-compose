package com.illu.demo.ui.home.hot

import androidx.lifecycle.MutableLiveData
import com.illu.demo.base.BaseViewModel
import com.illu.demo.bean.ArticleBean
import com.illu.demo.common.UserManager
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.isLogin
import com.illu.demo.common.loadmore.LoadMoreStatus