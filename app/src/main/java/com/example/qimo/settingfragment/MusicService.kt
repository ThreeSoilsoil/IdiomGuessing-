package com.example.qimo.settingfragment


import android.app.*

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder

import android.os.IBinder


class MusicService : Service() {
    val mediaPlayer = MediaPlayer()
    companion object{
        val commond = "Operate"
    }
    val binder = MusicBinder()

    inner class  MusicBinder: Binder(){}

    override fun onBind(intent: Intent): IBinder {
        return binder
    }


    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
//        mediaPlayer.setOnPreparedListener {
//            it.start()
//        }
//        mediaPlayer.setOnCompletionListener {
//            play()
//        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val operate = intent?.getIntExtra(commond,1)?:1
        when(operate){
            1->play()
            2->Stop()

        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun Stop() {
        mediaPlayer.pause()
    }

    fun play() {
        if(!mediaPlayer.isPlaying){
            mediaPlayer.start()
        }
    }
    private fun initMediaPlayer(){
        val assetManager = assets
        val fd =assetManager.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }


}
