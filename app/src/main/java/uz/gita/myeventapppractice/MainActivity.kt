package uz.gita.myeventapppractice

import android.Manifest.permission.POST_NOTIFICATIONS
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import uz.gita.myeventapppractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val adapter = Adapter()
    private val sharedPref = MySharedPref.getInstance()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("TTT: ", "Granted")
            } else {
                Log.i("TTT: ", "Denied")
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            ContextCompat.checkSelfPermission(
                this@MainActivity,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.FOREGROUND_SERVICE
            ) -> {
                requestPermissionLauncher.launch(
                    POST_NOTIFICATIONS
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    POST_NOTIFICATIONS
                )
            }
        }

        binding.recyclerView.adapter = adapter

        adapter.submitList(eventList)


        adapter.setListener { eventData ->

            val intent = Intent(this@MainActivity, MyService::class.java)
            intent.putExtra("COMMAND", eventData.action)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else startService(intent)

            when (eventData.action) {
                Intent.ACTION_SCREEN_ON -> sharedPref.screenOn = eventData.state
                Intent.ACTION_SCREEN_OFF -> sharedPref.screenOff = eventData.state
                Intent.ACTION_POWER_CONNECTED -> sharedPref.powerOn = eventData.state
                Intent.ACTION_POWER_DISCONNECTED -> sharedPref.powerOff = eventData.state
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> sharedPref.plane = eventData.state
                BluetoothAdapter.ACTION_STATE_CHANGED -> sharedPref.bluetooth = eventData.state
                ConnectivityManager.CONNECTIVITY_ACTION -> sharedPref.wifi = eventData.state
                Intent.ACTION_SHUTDOWN -> sharedPref.shutDown = eventData.state
                Intent.ACTION_BATTERY_LOW -> sharedPref.batteryLow = eventData.state
                Intent.ACTION_BATTERY_OKAY -> sharedPref.batteryOk = eventData.state
                Intent.ACTION_HEADSET_PLUG -> sharedPref.headPhones = eventData.state
            }
        }
    }
}