package com.illu.demo.ui.mine.collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.hjq.bar.OnTitleBarListener
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.home.hot.ArticleAdapter
import com.illu.demo.ui.web.WebActivity
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.include_reload.*

class CollectionActivity : BaseVmActivity<CollectionViewModel>() {

    private lateinit var mAdapter: ArticleAdapter

    override fun viewModelClass(): Class<CollectionViewModel> = CollectionViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_collection

    override fun initView() {
        titleBar.setOnTitleBarListener(this)
        mAdapter = ArticleAdapter().apply {
            bindToRecyclerView(recycleView)
            setLoadMoreView(CommonLoadMoreView())
            setOnLoadMoreListener({
                mViewModel.loadMore()
            }, recycleView)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityHelper.start(
                    WebActivity::class.java, mapOf(WebActivity.ARTICLE_DATA to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect) {
                    mViewModel.unCollect(article.originId)
                    removeItem(position)
                }
            }
        }
        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        btnReload.setOnClickListener {
            mViewModel.refreshData()
        }
    }

    override fun initData() {
        mViewModel.refreshData()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            refreshStatus.observe(this@CollectionActivity, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            emptyDataStatus.observe(this@CollectionActivity, Observer {
                emptyView.isVisible = it
            })
            loadMoreStatus.observe(this@CollectionActivity, Observer {
                when (it) {
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(this@CollectionActivity, Observer {
                reloadView.isVisible = it
            })
            articleList.observe(this@CollectionActivity, Observer {
                mAdapter.setNewData(it)
            })
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATE, this, Observer { (id, collect) ->
            if (collect) {
                mViewModel.refreshData()
            } else {
                val position = mAdapter.data.indexOfFirst { it.originId == id }
                if (position != -1) {
                    removeItem(position)
                }
            }
        })
    }

    private fun removeItem(position: Int) {
        mAdapter.remove(position)
        emptyView.isVisible = mAdapter.data.isEmpty()
    }
}