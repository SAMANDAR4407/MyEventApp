package uz.gita.myeventapppractice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat

/**
 *    Created by Kamolov Samandar on 20.06.2023 at 15:08
 */

class MyService : Service() {

    private val receiver = MyReceiver()
    private val pref = MySharedPref.getInstance()

    companion object {
        const val CHANNEL_ID = "channel"
    }

/*    private val adapter = Adapter()

    private val bluetooth  = BluetoothConnectivityReceiver()
    private val powerConnect  = ChargerConnectedReceiver()
    private val powerDisconnect  = ChargerDisconnectedReceiver()
    private val airplane  = AirplaneModeReceiver()
    private val screenOn  = ScreenOnReceiver()
    private val screenOff  = ScreenOffReceiver()
    private val wifiConnect  = WifiConnectivityReceiver()
    private val batteryLow = BatteryLowReceiver()
    private val batteryOk = BatteryFullReceiver()
    private val shutDown = ShutDownReceiver()
    private val headset = HeadsetConnectivityReceiver()*/

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate() {
        super.onCreate()
        createChannel()
        startService()
        registerAllIntents()
    }

    private fun registerAllIntents() {
        registerReceiver(receiver, IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(Intent.ACTION_HEADSET_PLUG)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_SHUTDOWN)
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        })
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "EVENT", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startService() {
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent
            .getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val notification = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Events")
            .setOngoing(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(createNotificationLayout())
            .build()

        startForeground(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun createNotificationLayout(): RemoteViews {
        val view = RemoteViews(this.packageName, R.layout.remote_view)
        view.setOnClickPendingIntent(R.id.closeButton, createPendingIntent())
        return view
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, MyService::class.java)
        intent.putExtra("STOP", "STOP")
        return PendingIntent.getService(
            this,
            1,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val command = intent?.extras?.getString("STOP")

        if (command == "STOP") {

            pref.apply {
                batteryLow = false
                batteryOk = false
                screenOn = false
                screenOff = false
                powerOn = false
                powerOff = false
                wifi = false
                bluetooth = false
                headPhones = false
                plane = false
                shutDown = false
            }

            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}