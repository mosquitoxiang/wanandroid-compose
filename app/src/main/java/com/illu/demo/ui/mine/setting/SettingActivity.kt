package com.illu.demo.ui.mine.setting

import com.illu.baselibrary.manager.SettingManager
import com.illu.baselibrary.utils.isNightMode
import com.illu.baselibrary.utils.setNightMode
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseVmActivity<SettingViewModel>() {

    override fun viewModelClass(): Class<SettingViewModel> = SettingViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        titleBar.setOnTitleBarListener(this)
        scDayNight.apply {
            isChecked = isNightMode(this@SettingActivity)
            setOnCheckedChangeListener { _, boolean ->
                setNightMode(boolean)
                SettingManager.setNightMode(boolean)
            }
        }
    }

}