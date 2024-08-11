package com.mtsapps.eteration.commons.utils

import android.view.View

fun View.clickWithDebounce(debounceTime: Long = 600L, action: (View) -> Unit) {
    var lastClickTime = 0L
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > debounceTime) {
            lastClickTime = currentTime
            action(it)
        }
    }
}
fun View.changeVisibility(isVisible : Boolean){
    if (isVisible){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}