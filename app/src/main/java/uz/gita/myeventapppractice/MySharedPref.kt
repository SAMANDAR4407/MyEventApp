package uz.gita.myeventapppractice

import android.content.Context
import android.content.SharedPreferences

class MySharedPref private constructor(context: Context) {

    companion object {
        private lateinit var instance: MySharedPref

        fun init(context: Context) {
            instance = MySharedPref(context)
        }

        fun getInstance() = instance
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPref.edit()

    var screenOn: Boolean
    get() = sharedPref.getBoolean("screen_on",false)
    set(value) = editor.putBoolean("screen_on",value).apply()

    var screenOff: Boolean
        get() = sharedPref.getBoolean("screen_off",false)
        set(value) = editor.putBoolean("screen_off",value).apply()

    var powerOn: Boolean
        get() = sharedPref.getBoolean("power_on",false)
        set(value) = editor.putBoolean("power_on",value).apply()

    var powerOff: Boolean
        get() = sharedPref.getBoolean("power_off",false)
        set(value) = editor.putBoolean("power_off",value).apply()

    var plane: Boolean
        get() = sharedPref.getBoolean("plane",false)
        set(value) = editor.putBoolean("plane",value).apply()

    var bluetooth: Boolean
        get() = sharedPref.getBoolean("bluetooth",false)
        set(value) = editor.putBoolean("bluetooth",value).apply()

    var headPhones: Boolean
        get() = sharedPref.getBoolean("headPhones",false)
        set(value) = editor.putBoolean("headPhones",value).apply()

    var wifi: Boolean
        get() = sharedPref.getBoolean("wifi",false)
        set(value) = editor.putBoolean("wifi",value).apply()

    var batteryLow: Boolean
        get() = sharedPref.getBoolean("batteryLow",false)
        set(value) = editor.putBoolean("batteryLow",value).apply()

    var batteryOk: Boolean
        get() = sharedPref.getBoolean("batteryOk",false)
        set(value) = editor.putBoolean("batteryOk",value).apply()

    var shutDown: Boolean
        get() = sharedPref.getBoolean("shutDown",false)
        set(value) = editor.putBoolean("shutDown",value).apply()
}