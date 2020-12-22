package com.example.qimo.settingfragment

import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.qimo.R
import kotlinx.android.synthetic.main.activity_music.*


const val MyReceiverAction = "com.example.a2018110334__py.newmusic"

class MusicActivity : AppCompatActivity(),ServiceConnection {


    var binder:MusicService.MusicBinder?=null
    lateinit var receiver: MusicRecever


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        val intentFilter =IntentFilter()
        intentFilter.addAction(MyReceiverAction)
        receiver =MusicRecever()
        registerReceiver(receiver, intentFilter)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        } else {
            startMusicService()
        }

        music_start.setOnClickListener {
            val intent = Intent(this, MusicService::class.java)
            intent.putExtra(MusicService.commond, 1)
            startService(intent)

        }
        music_stop.setOnClickListener {
            val intent = Intent(this, MusicService::class.java)
            intent.putExtra(MusicService.commond, 2)
            startService(intent)

        }


    }




    fun startMusicService() {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.commond, 1)
        startService(intent)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        startMusicService()
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent =Intent(this, MusicRecever::class.java)
        unregisterReceiver(receiver)
        unbindService(this)

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        binder = service as MusicService.MusicBinder

    }
    inner class MusicRecever : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            val op = intent.getIntExtra(MusicService.commond, 1)

        }

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        binder = null
    }
}