package com.illu.demo.ui.login

import androidx.lifecycle.Observer
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.ext.clickWithTrigger
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity
import com.illu.demo.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVmActivity<LoginViewModel>() {
    override fun viewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        btnLogin.clickWithTrigger {
            tilAccount.error = ""
            tilPassword.error = ""
            val tietAccount = tietAccount.text.toString().trim()
            val tietPassword = tietPassword.text.toString().trim()
            when {
                tietAccount.isEmpty() -> tilAccount.error = getString(R.string.account_not_empty)
                tietPassword.isEmpty() -> tilPassword.error = getString(R.string.password_not_empty)
                else -> mViewModel.login(tietAccount, tietPassword)
            }
        }
        tvGoRegister.clickWithTrigger {
            ActivityHelper.start(RegisterActivity::class.java)
        }
    }

    override fun observer() {
        mViewModel.run {
            loginStatus.observe(this@LoginActivity, Observer {
                if (it) showProgressDialog(R.string.is_login) else dismissProgressDialog()
            })
            loginResult.observe(this@LoginActivity, Observer {
                if (it) {
                    ActivityHelper.finish(LoginActivity::class.java)
                }
            })
        }
    }

}