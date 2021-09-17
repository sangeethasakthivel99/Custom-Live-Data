package com.sangeetha.customlivedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class LifeCycleObserver<T>(
    val owner: LifecycleOwner,
    val observer: Observer<T>,
    private val callbackListener: CallbackListener<T>
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStarted() {
        callbackListener.notify(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResumed() {
        callbackListener.notify(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroyed() {
        owner.lifecycle.removeObserver(this)
        callbackListener.destroy(observer)
    }

}

interface CallbackListener<T> {
    fun notify(lifecycle: LifeCycleObserver<T>)
    fun destroy(observer: Observer<T>)
}
