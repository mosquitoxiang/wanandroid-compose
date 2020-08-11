package com.illu.demo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.illu.demo.base.BaseActivity
import com.illu.baselibrary.utils.showToast
import com.illu.demo.R
import com.illu.demo.ui.find.FindFragment
import com.illu.demo.ui.home.HomeFragment
import com.illu.demo.ui.mine.MineFragment
import com.illu.demo.ui.navigation.NavigationFragment
import com.illu.demo.ui.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var fragments: Map<Int, Fragment>
    private var previousTimeMillis = 0L

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
//            setOnNavigationItemReselectedListener { menuItem ->
//                val fragment = fragments[menuItem.itemId]
//                if (fragment is ScrollToTop) {
//                    fragment.scrollToTop()
//                }
//            }
        }

//        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            bottomNav.selectedItemId = initialItemId
//            showFragment(initialItemId)
//        }
    }

    override fun initView() {}

    override fun initData() {}

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.INSTANCE()
                SystemFragment::class.java -> SystemFragment.INSTANCE()
                FindFragment::class.java -> FindFragment.INSTANCE()
                NavigationFragment::class.java -> NavigationFragment.INSTANCE()
                MineFragment::class.java -> MineFragment.INSTANCE()
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