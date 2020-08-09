package com.illu.baselibrary.base

import androidx.lifecycle.*
import com.google.gson.JsonParseException
import com.illu.baselibrary.App
import com.illu.baselibrary.R
import com.illu.baselibrary.net.ApiException
import com.illu.baselibrary.utils.showToast
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel(){

    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    protected fun launch(
        block: Block<Unit>,
        error: Error ?= null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ) : Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            }catch (e: Exception) {
                when(e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                    }
                }
            }
        }
    }

    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    protected fun cancelJo(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    private fun onError(e: Exception, showErrorToast: Boolean) {
        when(e) {
            is ApiException -> {
                when(e.code) {
                    -1001 -> {

                    }
                    -1 -> if (showErrorToast) App.INSTANCE.showToast(e.message)
                    else -> if (showErrorToast) App.INSTANCE.showToast(e.message)
                }
            }
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException ->
                if (showErrorToast) App.INSTANCE.showToast(R.string.net_request_failed)
            is JsonParseException ->
                if (showErrorToast) App.INSTANCE.showToast(R.string.data_parse_error)
            else ->
                if (showErrorToast) App.INSTANCE.showToast(e.message ?: return)
        }
    }
}