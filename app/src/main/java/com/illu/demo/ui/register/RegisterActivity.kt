package com.illu.demo.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.illu.baselibrary.core.ActivityHelper
import com.illu.baselibrary.ext.clickWithTrigger
import com.illu.demo.R
import com.illu.demo.base.BaseVmActivity
import com.illu.demo.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseVmActivity<RegisterViewModel>() {

    override fun viewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initView() {
        btnRegister.clickWithTrigger {
            tilAccount.error = ""
            tilPassword.error = ""
            tilConfirmPssword.error = ""
            val tietAccount = tietAccount.text.toString().trim()
            val tietPassword = tietPassword.text.toString().trim()
            val tietConfirm = tietConfirmPssword.text.toString().trim()
            when {
                tietAccount.isEmpty() -> tilAccount.error = getString(R.string.account_not_empty)
                tietPassword.isEmpty() -> tilPassword.error = getString(R.string.password_not_empty)
                !tietConfirm.equals(tietPassword) -> tilConfirmPssword.error = getString(R.string.input_different)
                else -> mViewModel.register(tietAccount, tietPassword, tietConfirm)
            }
        }
    }

    override fun observer() {
        mViewModel.run {
            registerStatus.observe(this@RegisterActivity, Observer {
                if (it) showProgressDialog(R.string.loading) else dismissProgressDialog()
            })
            registerResult.observe(this@RegisterActivity, Observer {
                if (it) ActivityHelper.finish(LoginActivity::class.java, RegisterActivity::class.java)
            })
        }
    }
}