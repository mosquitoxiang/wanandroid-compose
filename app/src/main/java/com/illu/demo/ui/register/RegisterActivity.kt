package com.illu.demo.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity

class RegisterActivity : BaseVmActivity<RegisterViewModel>() {

    override fun viewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initView() {
    }
}