package com.illu.demo.ui

import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.google.android.material.animation.AnimationUtils
import com.illu.demo.base.BaseActivity
import com.illu.baselibrary.utils.showToast
import com.illu.demo.R
import com.illu.demo.common.ScrollToTop
import com.illu.demo.ui.find.FindFragment
import com.illu.demo.ui.home.HomeFragment
import com.illu.demo.ui.mine.MineFragment
import com.illu.demo.ui.navigation.NavigationFragment
import com.illu.demo.ui.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var fragments: Map<Int, Fragment>
    private var previousTimeMillis = 0L
    private var currentBottomNavagtionState = true
    private var bottomNavigationViewAnimtor: ViewPropertyAnimator? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.find to createFragment(FindFragment::class.java),
            R.id.navigation to createFragment(NavigationFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java))

        bottomNav.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener { menuItem ->
                val fragment = fragments[menuItem.itemId]
                if (fragment is ScrollToTop) {
                    fragment.scrollToTop()
                }
            }
        }

        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            bottomNav.selectedItemId = initialItemId
            showFragment(initialItemId)
        }
    }

    override fun initView() {}

    override fun initData() {}

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.instance()
                SystemFragment::class.java -> SystemFragment.instance()
                FindFragment::class.java -> FindFragment.instance()
                NavigationFragment::class.java -> NavigationFragment.instance()
                MineFragment::class.java -> MineFragment.instance()
                else -> throw IllegalAccessException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFrament = fragments[menuItemId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFrament?.let {
                if (it.isAdded) show(it) else add(R.id.fl, it)
            }
        }.commit()
    }

    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavagtionState == show) {
            return
        }
        if (bottomNavigationViewAnimtor != null) {
            bottomNavigationViewAnimtor?.cancel()
            bottomNav.clearAnimation()
        }
        currentBottomNavagtionState = show
        val targetY = if (show) 0F else bottomNav.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimtor = bottomNav.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setUpdateListener { bottomNavigationViewAnimtor = null }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val currentTimeMills = System.currentTimeMillis()
        if (currentTimeMills - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            showToast("在按一次退出")
            previousTimeMillis = currentTimeMills
        }
    }

}