package com.sanjeet.applicationsessionmanager

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sanjeet.sessionmanager.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.plant(Timber.DebugTree());
        setContentView(R.layout.activity_main)



        val btn1: TextView = findViewById(R.id.btn1)
        val btn2: TextView = findViewById(R.id.btn2)

        // Access SessionManager from Application class
        sessionManager = (application as MyApplication).sessionManager

        btn1.setOnClickListener {
            // Retrieve and log all session data
            CoroutineScope(Dispatchers.Main).launch {
                sessionManager.getSessionData(this, deleteAfterRetrieving = false) { sessions ->
                    sessions.forEach { session ->
                        Timber.e("Session Record: id=${session.id}, start_time=${session.startTime}, end_time=${session.endTime}, duration=${session.duration}, is_online=${session.isOnline}")
                    }
                }
                Toast.makeText(this@MainActivity, "Session data logged to Timber", Toast.LENGTH_LONG).show()
            }
        }


        btn2.setOnClickListener {
            throw RuntimeException("App Crashed")
        }
    }
}