//package com.illu.demo.ui.web
//
//import android.os.Bundle
//import android.view.KeyEvent
//import android.webkit.WebView
//import android.widget.LinearLayout
//import com.illu.baselibrary.core.ActivityHelper
//import com.illu.baselibrary.ext.htmlToSpanned
//import com.illu.demo.R
//import com.illu.demo.base.BaseVmActivity
//import com.illu.demo.bean.ArticleBean
//import com.just.agentweb.AgentWeb
//import com.just.agentweb.DefaultWebClient
//import com.just.agentweb.WebChromeClient
//import kotlinx.android.synthetic.main.activity_web.*
//
//class WebActivity : BaseVmActivity<WebViewModel>() {
//
//    companion object {
//        const val ARTICLE_DATA = "article_data"
//    }
//
//    private lateinit var agentWeb: AgentWeb
//    private lateinit var article: ArticleBean
//
//    override fun viewModelClass(): Class<WebViewModel> = WebViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.activity_web
//
//    override fun initView() {
//        article = intent?.getParcelableExtra(ARTICLE_DATA) ?: return
//        titleBar.title = article.title.htmlToSpanned()
//        agentWeb = AgentWeb.with(this)
//            .setAgentWebParent(container, LinearLayout.LayoutParams(-1, -1))
//            .useDefaultIndicator()
//            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
//            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
//            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
//            .setWebChromeClient(object : WebChromeClient() {
//                override fun onReceivedTitle(view: WebView?, title: String?) {
//                    titleBar.title = title
//                    super.onReceivedTitle(view, title)
//                }
//            })
//            .interceptUnkownUrl()
//            .createAgentWeb()
//            .ready()
//            .go(article.link)
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (agentWeb.handleKeyEvent(keyCode, event)) {
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }
//
//    override fun onPause() {
//        agentWeb.webLifeCycle.onPause()
//        super.onPause()
//    }
//
//    override fun onResume() {
//        agentWeb.webLifeCycle.onResume()
//        super.onResume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        agentWeb.webLifeCycle.onDestroy()
//    }
//}