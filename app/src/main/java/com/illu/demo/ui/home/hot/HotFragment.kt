package com.illu.demo.ui.home.hot

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.include_reload.*

class HotFragment : BaseVmFragment<HotViewModel>() {

    companion object {
        fun instance() = HotFragment()
    }

    private lateinit var mAdapter: HotAdapter

    override fun viewModelClass(): Class<HotViewModel> = HotViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_hot

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        swiperRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticlelist() }
        }
        mAdapter = HotAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recycleView)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList()
            }, recycleView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
            }
            setOnItemClickListener { _, view, position ->
                val article = mAdapter.data[position]
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
        btnReload.setOnClickListener {
            mViewModel.refreshArticlelist()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swiperRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticlelist()
    }

}