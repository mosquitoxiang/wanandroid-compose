//package com.illu.demo.ui.find.share
//
//import android.os.Bundle
//import android.view.View
//import androidx.lifecycle.Observer
//import com.illu.baselibrary.core.ActivityHelper
//import com.illu.baselibrary.ext.hideSoftInput
//import com.illu.baselibrary.utils.showToast
//import com.illu.demo.R
//import com.illu.demo.base.BaseVmActivity
//import kotlinx.android.synthetic.main.activity_share.*
//
//class ShareActivity : BaseVmActivity<ShareViewModel>() {
//
//    override fun viewModelClass(): Class<ShareViewModel> = ShareViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.activity_share
//
//    override fun initView() {
//        titleBar.setOnTitleBarListener(this)
//    }
//
//    override fun onRightClick(v: View) {
//        val title = acetTitle.text.toString().trim()
//        val link = acetlink.text.toString().trim()
//        when {
//            title.isEmpty() -> {
//                showToast("标题不能为空")
//                return
//            }
//            link.isEmpty() -> {
//                showToast("链接不能为空")
//                return
//            }
//            else -> {
//                v.hideSoftInput()
//                mViewModel.share(title, link)
//            }
//        }
//    }
//
//    override fun initData() {
//        mViewModel.getUserInfo()
//    }
//
//    override fun observer() {
//        super.observer()
//        mViewModel.run {
//            shareResult.observe(this@ShareActivity, Observer {
//                if (it) {
//                    ActivityHelper.finish(ShareActivity::class.java)
//                    showToast("分享成功")
//                }
//                else showToast("分享失败")
//            })
//            submitStatus.observe(this@ShareActivity, Observer {
//                if (it) showProgressDialog(R.string.sharing_article) else dismissProgressDialog()
//            })
//            userName.observe(this@ShareActivity, Observer {
//                acetSharePeople.setText(it)
//            })
//        }
//    }
//}