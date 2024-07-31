package com.demo.archive.customview.fozhu.view

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import com.blankj.utilcode.util.Utils
import com.demo.archive.customview.R


object SoundPlayer {
    private var soundIdMap = hashMapOf<Int, Int>()
    private val soundPool by lazy {
        SoundPool.Builder().apply {
            setMaxStreams(1)
            setAudioAttributes(AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build())
        }.build()
    }

    fun loadSound(audioRawId: Int) {
        if (!soundIdMap.containsKey(audioRawId)) {
            soundPool.load(Utils.getApp(), audioRawId, 1).let {
                soundIdMap[audioRawId] = it
            }
        }
    }

    fun play(audioRawId: Int) {
        if (soundIdMap.containsKey(audioRawId)) {
            soundPool.play(soundIdMap[audioRawId]!!, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }
}