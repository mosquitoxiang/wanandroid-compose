//package com.illu.demo.ui.mine.rank.minepoints
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import androidx.core.view.isVisible
//import androidx.lifecycle.Observer
//import com.illu.demo.R
//import com.illu.demo.base.BaseVmActivity
//import com.illu.demo.common.loadmore.CommonLoadMoreView
//import com.illu.demo.common.loadmore.LoadMoreStatus
//import kotlinx.android.synthetic.main.activity_mine_points.*
//import kotlinx.android.synthetic.main.header_mine_points.view.*
//
//class MinePointsActivity : BaseVmActivity<MinePointsViewModel>() {
//
//    private lateinit var mAdapter: MinePointsAdapter
//    private lateinit var mHeaderView: View
//
//    override fun viewModelClass(): Class<MinePointsViewModel> = MinePointsViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.activity_mine_points
//
//    override fun initView() {
//        titleBar.setOnTitleBarListener(this)
//        mHeaderView = LayoutInflater.from(this).inflate(R.layout.header_mine_points, null)
//        mAdapter = MinePointsAdapter().apply {
//            bindToRecyclerView(rv)
//            setLoadMoreView(CommonLoadMoreView())
//            setOnLoadMoreListener({
//                mViewModel.loadMore()
//            }, rv)
//        }
//        swipeRefreshLayout.run {
//            setColorSchemeResources(R.color.textColorPrimary)
//            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
//            setOnRefreshListener { mViewModel.refreshData() }
//        }
//        reloadView.setOnClickListener {
//            mViewModel.refreshData()
//        }
//    }
//
//    override fun initData() {
//        mViewModel.refreshData()
//    }
//
//    override fun observer() {
//        super.observer()
//        mViewModel.run {
//            refreshStatus.observe(this@MinePointsActivity, Observer {
//                swipeRefreshLayout.isRefreshing = it
//            })
//            loadMoreStatus.observe(this@MinePointsActivity, Observer {
//                when (it) {
//                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
//                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
//                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
//                    else -> return@Observer
//                }
//            })
//            reloadStatus.observe(this@MinePointsActivity, Observer {
//                reloadView.isVisible = it
//            })
//            minePoints.observe(this@MinePointsActivity, Observer {
//                if (mAdapter.headerLayoutCount == 0) {
//                    mAdapter.setHeaderView(mHeaderView)
//                }
//                mHeaderView.tvTotalPoints.text = it.coinCount.toString()
//                mHeaderView.tvLevelRank.text = getString(R.string.level_rank, it.level, it.rank)
//            })
//            minePointsList.observe(this@MinePointsActivity, Observer {
//                mAdapter.setNewData(it)
//            })
//        }
//    }
//
//}