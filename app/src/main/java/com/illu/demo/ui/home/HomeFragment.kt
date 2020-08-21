package com.illu.demo.ui.home

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.illu.baselibrary.core.ActivityHelper
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R
import com.illu.demo.base.BaseFragmentPagerAdapter
import com.illu.demo.common.ScrollToTop
import com.illu.demo.ui.MainActivity
import com.illu.demo.ui.home.gzh.GzhFragment
import com.illu.demo.ui.home.hot.HotFragment
import com.illu.demo.ui.home.project.ProjectFragment
import com.illu.demo.ui.home.search.SearchActivity
import com.illu.demo.ui.home.square.SquareFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.appBarLayout
import kotlinx.android.synthetic.main.fragment_home.tabLayout

class HomeFragment : BaseVmFragment<HomeViewModel>(), ScrollToTop {

    private lateinit var fragments: List<Fragment>
    private var currentOffset = 0

    companion object {
        fun instance() = HomeFragment()
    }

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        fragments = listOf(
            HotFragment.instance(),
            SquareFragment.instance(),
            ProjectFragment.instance(),
            GzhFragment.instance()
        )

        val titles = listOf(
            getString(R.string.hot),
            getString(R.string.square),
            getString(R.string.project),
            getString(R.string.gzh)
        )
        viewPager.adapter = BaseFragmentPagerAdapter(
            fm = childFragmentManager,
            fragments = fragments,
            titles = titles
        )
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(viewPager)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (activity is MainActivity && this.currentOffset != verticalOffset) {
                (activity as MainActivity).animateBottomNavigationView(verticalOffset > currentOffset)
                currentOffset = verticalOffset
            }
        })
        toolbar.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            val bundle = ActivityOptionsCompat
                .makeSceneTransitionAnimation(requireActivity(), toolbar, "toolbar").toBundle()
            startActivity(intent, bundle)
        }
    }

    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment = fragments[viewPager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }

}