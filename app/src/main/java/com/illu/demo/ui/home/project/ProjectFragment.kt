package com.illu.demo.ui.home.project

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.home.hot.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.include_reload.*


class ProjectFragment : BaseVmFragment<ProjectViewModel>() {

    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var mTreeAdapter: ProjectTreeAdapter

    companion object {
        fun instance() = ProjectFragment()
    }
    override fun viewModelClass(): Class<ProjectViewModel> = ProjectViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initView() {
        mTreeAdapter = ProjectTreeAdapter().apply {
            bindToRecyclerView(rvCategory)
            onCheckedListener = {
                mViewModel.changePosition(it)
            }
        }
        mArticleAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recycleview)
            setOnLoadMoreListener({
                mViewModel.loadMoreData()
            }, recycleview)
        }
        btnReload.setOnClickListener {
            mViewModel.getTreeData()
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.getTreeData() }
        }
    }

    override fun lazyLoadData() {
        mViewModel.getTreeData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            treeListLiveData.observe(viewLifecycleOwner, Observer {
                rvCategory.isGone = it.isEmpty()
                mTreeAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
            childListLiveData.observe(viewLifecycleOwner, Observer {
                mArticleAdapter.setNewData(it)
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mArticleAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mArticleAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mArticleAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
        }
    }

}