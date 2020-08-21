package com.illu.baselibrary.core

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.illu.baselibrary.ext.putExtras
import com.illu.baselibrary.lifecycles.BaseLifecycleCallbacks

object ActivityHelper {

    private val activities = mutableListOf<Activity>()

    fun init(application: Application){
        application.registerActivityLifecycleCallbacks(BaseLifecycleCallbacks(
            onActivityCreated = {activity, _ ->
                activities.add(activity)
            },
            onActivityDestroyed = {activity ->
                activities.remove(activity)
            }
        ))
    }

    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()){
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    fun finish(vararg clazz: Class<out Activity>){
        activities.forEach { activity ->
            if (clazz.contains(activity::class.java)){
                activity.finish()
            }
        }
    }

    fun getCurrentActivity(): Activity{
        if (activities.size > 0){
            return activities[activities.lastIndex]
        }else{
            throw ActivityNotFoundException("please init first!")
        }
    }
}