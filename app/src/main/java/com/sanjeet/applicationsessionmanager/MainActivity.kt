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

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.plant(Timber.DebugTree());
        setContentView(R.layout.activity_main)



        val btn: TextView = findViewById(R.id.txt)

        // Access SessionManager from Application class
        sessionManager = (application as MyApplication).sessionManager

        btn.setOnClickListener {
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
    }
}