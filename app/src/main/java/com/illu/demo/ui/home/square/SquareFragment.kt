package com.illu.demo.ui.home.square

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.bus.USER_LOGIN_STATE_CHANGED
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_square.*
import kotlinx.android.synthetic.main.include_reload.*

class SquareFragment : BaseVmFragment<SquareViewModel>() {

    private lateinit var mAdapter: SquareAdapter

    companion object {
        fun instance() = SquareFragment()
    }

    override fun viewModelClass(): Class<SquareViewModel> = SquareViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_square

    override fun initView() {
        mAdapter = SquareAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMore()
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityHelper.start(WebActivity::class.java, mapOf(WebActivity.ARTICLE_DATA to article))
            }
            setOnItemChildClickListener { _, view, position ->
                val article=  mAdapter.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.getSquareData() }
        }
        btnReload.setOnClickListener {
            mViewModel.getSquareData()
        }
    }

    override fun lazyLoadData() {
        mViewModel.getSquareData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
            Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
                mViewModel.updateListCollectState()
            })
            Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
                mViewModel.updateItemCollectState(it)
            })
        }
    }
}