package com.illu.demo.ui.navigation

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R
import com.illu.demo.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.include_reload.*

class NavigationFragment : BaseVmFragment<NavigationViewModel>() {

    companion object {
        fun instance() = NavigationFragment()
    }

    private lateinit var navAdapter: NavAdapter

    override fun viewModelClass(): Class<NavigationViewModel> = NavigationViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    override fun initView() {
        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        navAdapter = NavAdapter().apply {
            bindToRecyclerView(rv)
        }
        rv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            LogUtil.d("scrollY:${scrollY} oldScrollY:${oldScrollY}")
            if (activity is MainActivity && scrollY != oldScrollY) {
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
            val layoutManager = rv.layoutManager as LinearLayoutManager
            tvFloatTitle.text = navAdapter.data[layoutManager.findFirstVisibleItemPosition()].name
        }
        btnReload.setOnClickListener { mViewModel.refreshData() }
    }

    override fun lazyLoadData() {
        mViewModel.refreshData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            navList.observe(viewLifecycleOwner, Observer {
                tvFloatTitle.isGone = it.isEmpty()
                tvFloatTitle.text = it[0].name
                navAdapter.setNewData(it)
            })
        }
    }
}