//package com.illu.demo.ui.system
//
//import android.view.View
//import androidx.core.view.isVisible
//import androidx.lifecycle.Observer
//import com.google.android.material.appbar.AppBarLayout
//import com.illu.demo.base.BaseVmFragment
//import com.illu.demo.R
//import com.illu.demo.base.BaseFragmentPagerAdapter
//import com.illu.demo.common.ScrollToTop
//import com.illu.demo.ui.home.project.CategoryBean
//import com.illu.demo.ui.system.category.SystemCategoryFragment
//import com.illu.demo.ui.system.pager.SystemPagerFragment
//
//class SystemFragment : BaseVmFragment<SystemViewModel>(), ScrollToTop {
//
//    companion object {
//        fun instance() = SystemFragment()
//    }
//
//    private var titles = mutableListOf<String>()
//    private var fragments = mutableListOf<SystemPagerFragment>()
//    private var currentOffset = 0
//    private var categoryFragment: SystemCategoryFragment? = null
//
//    override fun viewModelClass(): Class<SystemViewModel> = SystemViewModel::class.java
//
//    override fun getLayoutId(): Int = R.layout.fragment_system
//
//    override fun initView() {
//        btnReload.setOnClickListener {
//            mViewModel.getCategory()
//        }
//        ivFilter.setOnClickListener {
//            categoryFragment?.show(childFragmentManager)
//        }
//        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
//            if (activity is MainActivity && currentOffset != offset) {
//                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
//                currentOffset = offset
//            }
//        })
//    }
//
//    override fun lazyLoadData() {
//        mViewModel.getCategory()
//    }
//
//    override fun observe() {
//        super.observe()
//        mViewModel.run {
//            categoryList.observe(viewLifecycleOwner, Observer {
//                ivFilter.visibility = View.VISIBLE
//                tabLayout.visibility = View.VISIBLE
//                viewPager.visibility = View.VISIBLE
//                setUp(it)
//                categoryFragment = SystemCategoryFragment.instance(ArrayList(it))
//            })
//            loadingStatus.observe(viewLifecycleOwner, Observer {
//                progressBar.isVisible = it
//            })
//            reloadStatus.observe(viewLifecycleOwner, Observer {
//                reloadView.isVisible = it
//            })
//        }
//    }
//
//    private fun setUp(categories: MutableList<CategoryBean>) {
//        titles.clear()
//        fragments.clear()
//        categories.forEach {
//            titles.add(it.name!!)
//            fragments.add(SystemPagerFragment.instance(it.children))
//        }
//        viewPager.offscreenPageLimit = titles.size
//        viewPager.adapter = BaseFragmentPagerAdapter(
//            childFragmentManager,
//            fragments,
//            titles
//        )
//        tabLayout.setupWithViewPager(viewPager)
//    }
//
//    override fun scrollToTop() {
//        if (fragments.isEmpty() || viewPager == null) return
//        fragments[viewPager.currentItem].scrollToTop()
//    }
//
//    fun getCurrentChecked(): Pair<Int, Int> {
//        if (fragments.isEmpty() || viewPager == null) return 0 to 0
//        val first = viewPager.currentItem
//        val second = fragments[viewPager.currentItem].checkedPosition
//        return first to second
//    }
//
//    fun check(position: Pair<Int, Int>) {
//        if (fragments.isEmpty() || viewPager == null) return
//        viewPager.currentItem = position.first
//        fragments[viewPager.currentItem].check(position.second)
//    }
//}