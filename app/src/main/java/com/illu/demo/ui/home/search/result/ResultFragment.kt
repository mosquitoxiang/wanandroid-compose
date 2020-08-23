package com.illu.demo.ui.home.search.result

import androidx.core.view.isGone
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
import com.illu.demo.ui.home.hot.ArticleAdapter
import com.illu.demo.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_result.swipeRefreshLayout
import kotlinx.android.synthetic.main.include_reload.*


class ResultFragment : BaseVmFragment<ResultViewModel>() {

    companion object {
        fun instance(): ResultFragment {
            return ResultFragment()
        }
    }

    private lateinit var mAdapter: ArticleAdapter
    private var content: String? = ""

    override fun viewModelClass(): Class<ResultViewModel> = ResultViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_result
    }

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.search(content!!) }
        }
        mAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(rv)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList()
            }, rv)
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityHelper.start(
                    WebActivity::class.java,
                    mapOf(WebActivity.ARTICLE_DATA to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
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
            mViewModel.search(content!!)
        }
    }

    fun doSearch(content: String) {
        this.content = content
        mViewModel.search(content)
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
                mAdapter.disableLoadMoreIfNotFullPage(rv)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            emptyStatus.observe(viewLifecycleOwner, Observer {
                emptyView.isVisible = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner, Observer {
            mViewModel.updateListCollectState()
        })
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, viewLifecycleOwner, Observer {
            mViewModel.updateItemCollectState(it)
        })
    }

}