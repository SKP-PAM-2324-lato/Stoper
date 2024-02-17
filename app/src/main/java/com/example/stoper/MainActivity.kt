package com.example.stoper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopwatch = findViewById(R.id.chronometer2)

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
}