package com.sangeetha.customlivedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class CustomLiveData<T>: CallbackListener<T> {

    private var value: T? = null

    private val observers: MutableMap<Observer<T>, LifeCycleObserver<T>> = mutableMapOf()

    fun postValue(data: T) {
        value = data
        notifyObservers()
    }

    private fun notifyObservers() {
        observers.values.forEach { lifecycleObserver ->
            if (lifecycleObserver.owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            notify(lifecycleObserver)
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        LifeCycleObserver(lifecycleOwner, observer, this).apply {
            observers[observer] = this
            lifecycleOwner.lifecycle.addObserver(this)
        }
    }

    override fun notify(lifecycle: LifeCycleObserver<T>) {
        lifecycle.observer.invoke(value)
    }

    override fun destroy(observer: Observer<T>) {
        observers.remove(observer)
    }

}

typealias Observer<T> = (event: T?) -> Unit