package com.example.stoper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopwatch = findViewById(R.id.chronometer2)

        // odtworzenie stanu
        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else{
                setBaseTime()
            }
        }

        val startButton = findViewById<Button>(R.id.button_start)
        startButton.setOnClickListener {
            if(!running){
                setBaseTime()
                stopwatch.start()
                running = true
            }

        }

        val stopButton = findViewById<Button>(R.id.button_reset)
        stopButton.setOnClickListener {
            stopwatch.stop()
            offset = 0 
            running = false

        }

        val pauseButton = findViewById<Button>(R.id.button_pause)
        pauseButton.setOnClickListener {
            if(running){
                offset = SystemClock.elapsedRealtime() - stopwatch.base
                stopwatch.stop()
                running = false
            }
        }

    }

    fun setBaseTime(){
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)

        super.onSaveInstanceState(savedInstanceState)

    }

    override fun onStop() {
        super.onStop()
        if(running){
            offset = SystemClock.elapsedRealtime() - stopwatch.base
            stopwatch.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if(running){
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }
}