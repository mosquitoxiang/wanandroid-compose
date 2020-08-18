package com.illu.demo.ui.mine

import android.annotation.SuppressLint
import android.view.View
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.utils.LogUtil
import com.illu.demo.base.BaseVmFragment
import com.illu.demo.R
import com.illu.demo.common.UserManager
import com.illu.demo.common.isLogin
import com.illu.demo.ui.login.LoginActivity
import com.illu.demo.ui.mine.collection.CollectionActivity
import com.illu.demo.ui.mine.rank.minepoints.MinePointsActivity
import com.illu.demo.ui.mine.rank.pointsrank.RankActivity
import com.illu.demo.ui.mine.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVmFragment<MineViewModel>() {

    companion object {
        fun instance() = MineFragment()
    }

    override fun viewModelClass(): Class<MineViewModel>  = MineViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_mine

    @SuppressLint("SetTextI18n")
    override fun initView() {
        if (isLogin()) {
            tvName.apply {
                text = UserManager.getUserInfo()?.username
                visibility = View.VISIBLE
            }
            tvId.apply {
                text = "ID: ${UserManager.getUserInfo()?.id.toString()}"
                visibility = View.VISIBLE
            }
            textView.visibility = View.GONE
        }
        cslLogin.setOnClickListener {
            checkLogin()
        }
        llMinePoints.setOnClickListener {
            checkLogin{ ActivityHelper.start(MinePointsActivity::class.java) }
        }
        llPointsRank.setOnClickListener {
            checkLogin{ ActivityHelper.start(RankActivity::class.java) }
        }
        llMineShare.setOnClickListener {
            checkLogin()
        }
        llMineCollect.setOnClickListener {
            checkLogin { ActivityHelper.start(CollectionActivity::class.java) }
        }
        llHistory.setOnClickListener {

        }
        llOpenSource.setOnClickListener {

        }
        llAboutAuthor.setOnClickListener {

        }
        llSetting.setOnClickListener {
            ActivityHelper.start(SettingActivity::class.java)
        }
    }

    override fun initData() {

    }
}