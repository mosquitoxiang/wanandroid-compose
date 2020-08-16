package com.illu.demo.ui.home.square

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
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
            setOnItemChildClickListener { _, _, position ->

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
            sqareDataList.observe(viewLifecycleOwner, Observer {
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
        }
    }
}