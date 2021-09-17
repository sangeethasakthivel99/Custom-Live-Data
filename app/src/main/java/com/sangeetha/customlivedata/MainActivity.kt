package com.sangeetha.customlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val customLiveData = CustomLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputET.addTextChangedListener {
            customLiveData.postValue(it.toString())
        }

        customLiveData.observe(this) {
            outputTV.text = it
        }
    }
}