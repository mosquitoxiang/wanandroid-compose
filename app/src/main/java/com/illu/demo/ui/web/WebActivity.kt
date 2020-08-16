package com.illu.demo.ui.web

import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity

class WebActivity : BaseVmActivity<WebViewModel>() {

    companion object {
        const val ARTICLE_DATA = "article_data"
    }

    override fun viewModelClass(): Class<WebViewModel> = WebViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_web

    override fun initView() {

    }
}