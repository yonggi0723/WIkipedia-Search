package com.yonggi.wikipediasearch.application

import android.app.Application
import com.yonggi.wikipediasearch.application.injectors.Injector

class YonggiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(applicationContext)
    }
}