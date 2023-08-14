package uz.gita.myeventapppractice

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.ConnectivityManager

/**
 *    Created by Kamolov Samandar on 11.05.2023 at 17:40
 */

private val sharedPref = MySharedPref.getInstance()

data class EventData(
    val id: Int = 0,
    val name: String = "unknown event",
    val action: String,
    var state: Boolean = false
)

val eventList = listOf(
    EventData(
        id = 2,
        name = "Airplane mode",
        action = Intent.ACTION_AIRPLANE_MODE_CHANGED,
        state = sharedPref.plane
    ),
    EventData(
        id = 3,
        name = "Connectivity",
        action = ConnectivityManager.CONNECTIVITY_ACTION,
        state = sharedPref.wifi
    ),
    EventData(
        id = 4,
        name = "Screen Off",
        action = Intent.ACTION_SCREEN_OFF,
        state = sharedPref.screenOff
    ),
    EventData(
        id = 5,
        name = "Screen On",
        action = Intent.ACTION_SCREEN_ON,
        state = sharedPref.screenOn
    ),
    EventData(
        id = 6,
        name = "Disconnect charging",
        action = Intent.ACTION_POWER_DISCONNECTED,
        state = sharedPref.powerOff
    ),
    EventData(
        id = 7,
        name = "Connect charging",
        action = Intent.ACTION_POWER_CONNECTED,
        state = sharedPref.powerOn
    ),
    EventData(
        id = 8,
        name = "Bluetooth connectivity",
        action = BluetoothAdapter.ACTION_STATE_CHANGED,
        state = sharedPref.bluetooth
    ),
    EventData(
        id = 9,
        name = "Headset connectivity",
        action = Intent.ACTION_HEADSET_PLUG,
        state = sharedPref.headPhones
    ),
    EventData(
        id = 10,
        name = "Battery Okay",
        action = Intent.ACTION_BATTERY_OKAY,
        state = sharedPref.batteryOk
    ),
    EventData(
        id = 11,
        name = "Battery Low",
        action = Intent.ACTION_BATTERY_LOW,
        state = sharedPref.batteryLow
    ),
    EventData(
        id = 12,
        name = "Shut Down",
        action = Intent.ACTION_SHUTDOWN,
        state = sharedPref.shutDown
    )
)