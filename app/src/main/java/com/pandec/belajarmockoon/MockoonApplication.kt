package com.pandec.belajarmockoon

import android.app.Application
import android.content.Context

class MockoonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }
    companion object {
        var context: Context? = null
    }
}