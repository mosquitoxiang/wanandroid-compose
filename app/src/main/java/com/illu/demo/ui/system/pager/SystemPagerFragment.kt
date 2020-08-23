package com.illu.demo.ui.system.pager

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.ext.dpToPxInt
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.ScrollToTop
import com.illu.demo.common.bus.Bus
import com.illu.demo.common.bus.USER_COLLECT_UPDATE
import com.illu.demo.common.bus.USER_LOGIN_STATE_CHANGED
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.home.project.CategoryAdapter
import com.illu.demo.ui.home.project.CategoryBean
import com.illu.demo.ui.home.square.SimpleArticleAdapter
import com.illu.demo.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_system_pager.*
import kotlinx.android.synthetic.main.include_reload.*

class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>(), ScrollToTop {

    companion object {

        private const val CATEGORY_LIST = "CATEGORY_LIST"

        fun instance(categoryList: ArrayList<CategoryBean>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGORY_LIST, categoryList)
                }
            }
        }
    }

    private lateinit var mArticleAdapter: SimpleArticleAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter
    private lateinit var categoryList: List<CategoryBean>

    var checkedPosition = 0

    override fun viewModelClass(): Class<SystemPagerViewModel> = SystemPagerViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_system_pager

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.changePosition(categoryList[checkedPosition].id)
            }
        }
        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        checkedPosition = 0
        mCategoryAdapter = CategoryAdapter().apply {
            bindToRecyclerView(rvCategory)
            setNewData(categoryList)
            onCheckedListener = {
                checkedPosition = it
                mViewModel.changePosition(categoryList[checkedPosition].id)
            }
        }
        mArticleAdapter = SimpleArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(rvArticle)
            setOnLoadMoreListener({
                mViewModel.loadMore(categoryList[checkedPosition].id)
            }, rvArticle)
            setOnItemClickListener { _, _, position ->
                val article = mArticleAdapter.data[position]
                ActivityHelper.start(
                    WebActivity::class.java,
                    mapOf(WebActivity.ARTICLE_DATA to article))            }
            setOnItemChildClickListener { _, view, position ->
                val article = mArticleAdapter.data[position]
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
            mViewModel.changePosition(categoryList[checkedPosition].id)
        }
    }

    override fun lazyLoadData() {
        mViewModel.changePosition(categoryList[checkedPosition].id)
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mArticleAdapter.setNewData(it)
                mArticleAdapter.disableLoadMoreIfNotFullPage(rvArticle)
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
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
        rvArticle.smoothScrollToPosition(0)
    }

    fun check(position: Int) {
        if (position != checkedPosition) {
            checkedPosition = position
            mCategoryAdapter.check(position)
            (rvCategory.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 8f.dpToPxInt())
            mViewModel.changePosition(categoryList[checkedPosition].id)
        }
    }
}