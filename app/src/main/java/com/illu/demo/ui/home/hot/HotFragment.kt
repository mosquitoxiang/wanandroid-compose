//package com.illu.demo.ui.home.hot
//
//import android.annotation.SuppressLint
//import androidx.core.view.isVisible
//import androidx.lifecycle.Observer
//import com.illu.baselibrary.core.ActivityHelper
//import com.illu.baselibrary.utils.LogUtil
//import com.illu.demo.R
//import com.illu.demo.base.BaseVmFragment
//import com.illu.demo.common.ScrollToTop
//import com.illu.demo.common.bus.Bus
//import com.illu.demo.common.bus.USER_COLLECT_UPDATE
//import com.illu.demo.common.bus.USER_LOGIN_STATE_CHANGED
//import com.illu.demo.common.loadmore.CommonLoadMoreView
//import com.illu.demo.common.loadmore.LoadMoreStatus
//import com.illu.demo.ui.web.WebActivity
//
//class HotFragment : BaseVmFragment<HotViewModel>(), ScrollToTop {
//
//    companion object {
//        fun instance() = HotFragment()
//    }
//
//    private lateinit var mAdapter: ArticleAdapter
//
//    override fun viewModelClass(): Class<HotViewModel> = HotViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.fragment_hot
//
//    @SuppressLint("ResourceAsColor")
//    override fun initView() {
//        swipeRefreshLayout.run {
//            setColorSchemeResources(R.color.textColorPrimary)
//            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
//            setOnRefreshListener { mViewModel.refreshArticlelist() }
//        }
//        mAdapter = ArticleAdapter().apply {
//            setLoadMoreView(CommonLoadMoreView())
//            bindToRecyclerView(recyclerView)
//            setOnLoadMoreListener({
//                mViewModel.loadMoreArticleList()
//            }, recyclerView)
//            setOnItemClickListener { _, _, position ->
//                val article = mAdapter.data[position]
//                ActivityHelper.start(
//                    WebActivity::class.java,
//                    mapOf(WebActivity.ARTICLE_DATA to article)
//                )
//            }
//            setOnItemChildClickListener { _, view, position ->
//                val article = mAdapter.data[position]
//                if (view.id == R.id.iv_collect && checkLogin()) {
//                    view.isSelected = !view.isSelected
//                    if (article.collect) {
//                        mViewModel.uncollect(article.id)
//                    } else {
//                        mViewModel.collect(article.id)
//                    }
//                }
//            }
//        }
//        btnReload.setOnClickListener {
//            mViewModel.refreshArticlelist()
//        }
//    }
//
//    override fun observe() {
//        super.observe()
//        mViewModel.run {
//            articleList.observe(viewLifecycleOwner, Observer {
//                mAdapter.setNewData(it)
//            })
//            refreshStatus.observe(viewLifecycleOwner, Observer {
//                swipeRefreshLayout.isRefreshing = it
//            })
//            loadMoreStatus.observe(viewLifecycleOwner, Observer {
//                when (it) {
//                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
//                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
//                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
//                    else -> return@Observer
//                }
//            })
//            reloadStatus.observe(viewLifecycleOwner, Observer {
//                reloadView.isVisible = it
//            })
//        }
//        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
//            mViewModel.updateListCollectState()
//        })
//        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
//            mViewModel.updateItemCollectState(it)
//        })
//    }
//
//    override fun lazyLoadData() {
//        mViewModel.refreshArticlelist()
//    }
//
//    override fun scrollToTop() {
//        recyclerView.smoothScrollToPosition(0)
//    }
//
//}