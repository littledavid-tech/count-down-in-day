package top.littledavid.countdowninday

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by IT on 7/20/2018.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {

        private var mContext: Context? = null

        fun getContextInstance(): Context {
            return mContext!!
        }
    }

}