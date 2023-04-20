//package com.illu.demo.ui.mine.rank.pointsrank
//
//import android.os.Bundle
//import androidx.core.view.isVisible
//import androidx.lifecycle.Observer
//import com.illu.demo.R
//import com.illu.demo.base.BaseVmActivity
//import com.illu.demo.common.loadmore.CommonLoadMoreView
//import com.illu.demo.common.loadmore.LoadMoreStatus
//import kotlinx.android.synthetic.main.activity_rank.*
//import kotlinx.android.synthetic.main.activity_rank.reloadView
//import kotlinx.android.synthetic.main.activity_rank.rv
//import kotlinx.android.synthetic.main.activity_rank.swipeRefreshLayout
//
//class RankActivity : BaseVmActivity<RankViewModel>() {
//
//    private lateinit var mAdapter: RankAdapter
//
//    override fun viewModelClass(): Class<RankViewModel> = RankViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.activity_rank
//
//    override fun initView() {
//        titleBar.setOnTitleBarListener(this)
//        mAdapter = RankAdapter().apply {
//            bindToRecyclerView(rv)
//            setLoadMoreView(CommonLoadMoreView())
//            setOnLoadMoreListener({
//                mViewModel.loadMore()
//            }, rv)
//        }
//        swipeRefreshLayout.run {
//            setColorSchemeResources(R.color.textColorPrimary)
//            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
//            setOnRefreshListener { mViewModel.requestData() }
//        }
//    }
//
//    override fun initData() {
//        mViewModel.requestData()
//    }
//
//    override fun observer() {
//        super.observer()
//        mViewModel.run {
//            refreshStatus.observe(this@RankActivity, Observer {
//                swipeRefreshLayout.isRefreshing = it
//            })
//            loadMoreStatus.observe(this@RankActivity, Observer {
//                when (it) {
//                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
//                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
//                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
//                    else -> return@Observer
//                }
//            })
//            reloadStatus.observe(this@RankActivity, Observer {
//                reloadView.isVisible = it
//            })
//            rankList.observe(this@RankActivity, Observer {
//                mAdapter.setNewData(it)
//            })
//        }
//    }
//}