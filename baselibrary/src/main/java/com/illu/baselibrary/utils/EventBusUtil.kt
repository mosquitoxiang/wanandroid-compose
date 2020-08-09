package com.zhangyu.util
import com.illu.niubilife.event.BaseEvent
import org.greenrobot.eventbus.EventBus

object EventBusUtil {

    fun register(subscribe: Any){
        EventBus.getDefault().register(subscribe)
    }

    fun unRegister(subscribe: Any){
        EventBus.getDefault().unregister(subscribe)
    }

    fun sendEvent(event: BaseEvent<Any>){
        EventBus.getDefault().post(event)
    }

    fun sendStickeyEvent(event: BaseEvent<Any>){
        EventBus.getDefault().postSticky(event)
    }
}