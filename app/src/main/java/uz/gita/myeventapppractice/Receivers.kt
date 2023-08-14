package uz.gita.myeventapppractice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.util.Log

/**
 *    Created by Kamolov Samandar on 11.05.2023 at 18:07
 */

/*
interface IntentValue {
    val intentString: String
}
*/

class MyReceiver: BroadcastReceiver() {
    private lateinit var mediaPlayer: MediaPlayer
    private val pref = MySharedPref.getInstance()
    var lastCommandTime = 0L

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val state = intent.getBooleanExtra("state", false)
                if (state){
                    if (pref.plane){
                        mediaPlayer = MediaPlayer.create(context, R.raw.airplane_mode)
                        mediaPlayer.start()
                    }
                }
            }
            BluetoothAdapter.ACTION_STATE_CHANGED -> {

                if (pref.bluetooth){
                    if (System.currentTimeMillis()-lastCommandTime < 1000) return
                    lastCommandTime = System.currentTimeMillis()
                    mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_state)
                    mediaPlayer.start()
                }
             /*   when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_OFF -> {
                        if (pref.bluetooth) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_state)
                            mediaPlayer.start()
                        }
                    }

                    BluetoothAdapter.STATE_ON -> {
                        if (pref.bluetooth) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_state)
                            mediaPlayer.start()
                        }
                    }
                }*/
            }
            Intent.ACTION_HEADSET_PLUG -> {
                if (pref.headPhones) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.iphonemessage)
                    mediaPlayer.start()
                } else {
                    mediaPlayer = MediaPlayer.create(context, R.raw.iphonemessage)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_SCREEN_ON -> {
                if (pref.screenOn){
                    mediaPlayer = MediaPlayer.create(context, R.raw.screen_on)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_SCREEN_OFF -> {
                if (pref.screenOff){
                    mediaPlayer = MediaPlayer.create(context, R.raw.screen_off)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_POWER_CONNECTED -> {
                if (pref.powerOn) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.charger_connect)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                if (pref.powerOff) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.charger_disconnect)
                    mediaPlayer.start()
                }
            }
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                if (pref.wifi){
                    mediaPlayer = MediaPlayer.create(context, R.raw.wifi_state)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_BATTERY_LOW -> {
                if (pref.batteryLow){
                    mediaPlayer = MediaPlayer.create(context, R.raw.low_battery)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_BATTERY_OKAY -> {
                if (pref.batteryOk){
                    mediaPlayer = MediaPlayer.create(context, R.raw.battery_okay)
                    mediaPlayer.start()
                }
            }
            Intent.ACTION_SHUTDOWN -> {
                if (pref.shutDown){
                    mediaPlayer = MediaPlayer.create(context, R.raw.shut_down)
                    mediaPlayer.start()
                }
            }
        }
    }

}

/*

class ScreenOnReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: ScreenOnReceiver")
        when(intent?.action){
            Intent.ACTION_SCREEN_ON -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.screen_on)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_SCREEN_ON
}
class ScreenOffReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: ScreenOffReceiver")
        when(intent?.action){
            Intent.ACTION_SCREEN_OFF -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.screen_off)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_SCREEN_OFF
}
class AirplaneModeReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: AirplaneModeReceiver")
        when(intent?.action){
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.airplane_mode)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_AIRPLANE_MODE_CHANGED
}
class ChargerConnectedReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: ChargerConnectedReceiver")
        when(intent?.action){
            Intent.ACTION_POWER_CONNECTED -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.charger_connect)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_POWER_CONNECTED
}
class ChargerDisconnectedReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: ChargerDisconnectedReceiver")
        when(intent?.action){
            Intent.ACTION_POWER_DISCONNECTED -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.charger_disconnect)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_POWER_DISCONNECTED
}
class TimeChangeReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: TimeChangeReceiver")
        when(intent?.action){
            Intent.ACTION_TIME_CHANGED -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.iphonemessage)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_TIME_CHANGED
}
class WifiConnectivityReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: WifiConnectivityReceiver ${intent?.action}")
        when(intent?.action){
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.wifi_state)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = ConnectivityManager.CONNECTIVITY_ACTION
}
class BluetoothConnectivityReceiver: BroadcastReceiver(), IntentValue {
    var lastCommandTime = 0L
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: BluetoothConnectivityReceiver ${intent?.action}")
        if (System.currentTimeMillis()-lastCommandTime < 1000) return
        lastCommandTime = System.currentTimeMillis()
        when(intent?.action){
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.bluetooth_state)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = BluetoothAdapter.ACTION_STATE_CHANGED
}
class HeadsetConnectivityReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: HeadsetConnectivityReceiver")
        when(intent?.action){
            Intent.ACTION_HEADSET_PLUG -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.iphonemessage)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_HEADSET_PLUG
}
class BatteryFullReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: BatteryFullReceiver")
        when(intent?.action){
            Intent.ACTION_BATTERY_OKAY -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.battery_okay)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_BATTERY_OKAY
}
class BatteryLowReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: BatteryLowReceiver")
        when(intent?.action){
            Intent.ACTION_BATTERY_LOW -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.low_battery)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_BATTERY_LOW
}
class ShutDownReceiver: BroadcastReceiver(), IntentValue {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("TTT", "onReceive: ShutDownReceiver")
        when(intent?.action){
            Intent.ACTION_SHUTDOWN -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.shut_down)
                mediaPlayer.start()
            }
        }
    }

    override val intentString: String = Intent.ACTION_SHUTDOWN
}*/
