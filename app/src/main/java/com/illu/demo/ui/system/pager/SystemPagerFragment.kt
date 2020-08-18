package com.illu.demo.ui.system.pager

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.R
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.common.loadmore.CommonLoadMoreView
import com.illu.demo.common.loadmore.LoadMoreStatus
import com.illu.demo.ui.home.project.CategoryAdapter
import com.illu.demo.ui.home.project.CategoryBean
import com.illu.demo.ui.home.square.SimpleArticleAdapter
import com.illu.demo.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_system_pager.*
import kotlinx.android.synthetic.main.include_reload.*

class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>() {

    private lateinit var mArticleAdapter: SimpleArticleAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter
    private lateinit var categoryList: List<CategoryBean>
    private var checkedPosition = 0

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

    override fun viewModelClass(): Class<SystemPagerViewModel> = SystemPagerViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_system_pager

    override fun initView() {
        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
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
                ActivityHelper.start(WebActivity::class.java)
            }
            setOnItemChildClickListener { _, view, position ->

            }
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.changePosition(categoryList[checkedPosition].id) }
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

        }
    }
}