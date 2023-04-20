//package com.illu.demo.ui.navigation
//
//import androidx.core.view.isGone
//import androidx.core.view.isVisible
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.illu.baselibrary.core.ActivityHelper
//import com.illu.baselibrary.utils.LogUtil
//import com.illu.demo.base.BaseVmFragment
//import com.illu.demo.R
//import com.illu.demo.ui.MainActivity
//import com.illu.demo.ui.web.WebActivity
//import kotlinx.android.synthetic.main.fragment_navigation.*
//import kotlinx.android.synthetic.main.include_reload.*
//
//class NavigationFragment : BaseVmFragment<NavigationViewModel>() {
//
//    companion object {
//        fun instance() = NavigationFragment()
//    }
//
//    private lateinit var navAdapter: NavAdapter
//    private var currentPosition = 0
//
//    override fun viewModelClass(): Class<NavigationViewModel> = NavigationViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.fragment_navigation
//
//    override fun initView() {
//        swipeRefreshLayout.apply {
//            setColorSchemeResources(R.color.textColorPrimary)
//            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
//            setOnRefreshListener { mViewModel.refreshData() }
//        }
//        navAdapter = NavAdapter().apply {
//            bindToRecyclerView(rv)
//            onItemTagClickListener = {
//                ActivityHelper.start(
//                    WebActivity::class.java,
//                    mapOf(WebActivity.ARTICLE_DATA to it)
//                )
//            }
//        }
//        rv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
//            if (activity is MainActivity && scrollY != oldScrollY) {
//                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
//            }
//            tvFloatTitle.text = navAdapter.data[currentPosition].name
//            val layoutManager = rv.layoutManager as LinearLayoutManager
//            val nextView = layoutManager.findViewByPosition(currentPosition + 1)
//            if (nextView != null) {
//                tvFloatTitle.y = if (nextView.top < tvFloatTitle.measuredHeight) {
//                    (nextView.top - tvFloatTitle.measuredHeight).toFloat()
//                } else 0f
//            }
//            currentPosition = layoutManager.findFirstVisibleItemPosition()
//        }
//        btnReload.setOnClickListener { mViewModel.refreshData() }
//    }
//
//    override fun lazyLoadData() {
//        mViewModel.refreshData()
//    }
//
//    override fun observe() {
//        super.observe()
//        mViewModel.run {
//            reloadStatus.observe(viewLifecycleOwner, Observer {
//                reloadView.isVisible = it
//            })
//            refreshStatus.observe(viewLifecycleOwner, Observer {
//                swipeRefreshLayout.isRefreshing = it
//            })
//            navList.observe(viewLifecycleOwner, Observer {
//                tvFloatTitle.isGone = it.isEmpty()
//                tvFloatTitle.text = it[0].name
//                navAdapter.setNewData(it)
//            })
//        }
//    }
//}