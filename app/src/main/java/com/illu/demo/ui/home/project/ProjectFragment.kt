package com.illu.demo.ui.home.project

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.ScrollToTop
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.bus.USER_LOGIN_STATE_CHANGED
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.home.hot.ArticleAdapter
import com.illu.demo.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.include_reload.*


class ProjectFragment : BaseVmFragment<ProjectViewModel>(), ScrollToTop {

    private lateinit var mTreeAdapter: CategoryAdapter
    private lateinit var mArticleAdapter: ArticleAdapter

    companion object {
        fun instance() = ProjectFragment()
    }
    override fun viewModelClass(): Class<ProjectViewModel> = ProjectViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initView() {
        mTreeAdapter = CategoryAdapter().apply {
            bindToRecyclerView(rvCategory)
            onCheckedListener = {
                mViewModel.changePosition(it)
            }
        }
        mArticleAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreData()
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mArticleAdapter.data[position]
                ActivityHelper.start(WebActivity::class.java, mapOf(WebActivity.ARTICLE_DATA to article))
            }
            setOnItemChildClickListener { _, view, position ->
                val article = mArticleAdapter.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.unCollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
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
            articleList.observe(viewLifecycleOwner, Observer {
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
            Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
                mViewModel.updateListCollectState()
            })
            Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
                mViewModel.updateItemCollectState(it)
            })
        }
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

}