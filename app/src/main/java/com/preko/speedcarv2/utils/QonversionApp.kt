package com.preko.speedcarv2.utils

import android.app.Application
import com.qonversion.android.sdk.Qonversion

class QonversionApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Qonversion.setDebugMode()
        Qonversion.launch(
            this, "yHCkQPsRKtJ3r76_916Ro3-0EBwoXV9P", false
        )
    }
}
