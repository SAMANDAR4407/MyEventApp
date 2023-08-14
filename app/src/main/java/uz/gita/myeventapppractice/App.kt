package uz.gita.myeventapppractice

import android.app.Application

/**
 *    Created by Kamolov Samandar on 21.06.2023 at 3:10
 */

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPref.init(this)
    }
}