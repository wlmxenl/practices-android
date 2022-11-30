package com.demo.archive

import android.app.Application
import com.blankj.utilcode.util.LogUtils

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        LogUtils.getConfig()
            .setLogHeadSwitch(false)
            .setBorderSwitch(false)
    }
}