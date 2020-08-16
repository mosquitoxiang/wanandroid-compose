package com.illu.demo.ui.home.gzh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.home.project.ProjectTreeAdapter
import com.illu.demo.ui.home.square.SquareAdapter
import kotlinx.android.synthetic.main.fragment_gzh.*

class GzhFragment : BaseVmFragment<GzhViewModel>() {

    companion object {
        fun instance() = GzhFragment()
    }

    private lateinit var mLeftAdapter: ProjectTreeAdapter
    private lateinit var mRightAdapter: SquareAdapter

    override fun viewModelClass(): Class<GzhViewModel> = GzhViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_gzh

    override fun initView() {
        mLeftAdapter = ProjectTreeAdapter().apply {
            bindToRecyclerView(rvLeft)
            onCheckedListener = {
                mViewModel.changeData(it)
            }
        }
        mRightAdapter = SquareAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(rvRight)
            setOnLoadMoreListener({
                mViewModel.loadMoreData()
            }, rvRight)
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.changeData() }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            freshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            authorList.observe(viewLifecycleOwner, Observer {
                mLeftAdapter.setNewData(it)
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
            loadingMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it) {
                    LoadMoreStatus.COMPLETED -> mRightAdapter.loadMoreComplete()
                    LoadMoreStatus.END -> mRightAdapter.loadMoreEnd()
                    LoadMoreStatus.ERROR -> mRightAdapter.loadMoreFail()
                    else -> return@Observer
                }
            })
            articleList.observe(viewLifecycleOwner, Observer {
                mRightAdapter.setNewData(it)
            })
        }
    }

    override fun lazyLoadData() {
        mViewModel.requestData()
    }
}