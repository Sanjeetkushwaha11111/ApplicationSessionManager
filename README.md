SessionManager Library
The SessionManager library is designed to help Android developers effortlessly manage user sessions in their applications. This library tracks the start and end times of sessions, records session details, and provides utility functions for retrieving session data. Additionally, it offers an optional crash handler to ensure session data integrity even if the app crashes.

Capabilities
Session Tracking: Automatically starts and ends sessions based on activity lifecycle events.
Database Integration: Uses Room Database to store session data locally.
Crash Handling: Optionally handle uncaught exceptions to end sessions gracefully when the app crashes.
Customizable: Users can customize the database name and add custom fields to the session records.
Data Retrieval: Provides functions to retrieve session data in various formats, including JSON.
Network Awareness: Tracks whether the session was online or offline.
Why You Need It
Managing user sessions is crucial for understanding user behavior, improving user experience, and gathering analytics. This library abstracts the complexity of session management, allowing developers to focus on building features rather than handling session tracking details. The optional crash handler ensures that session data is reliable, even in case of unexpected crashes.

How to Use
1. Add the Library Dependency
Add the library to your project dependencies:

groovy
Copy code
dependencies {
    implementation 'com.sanjeet:sessionmanager:1.0.0'
}
2. Initialize the Library in Your Application Class
Create or update your Application class to initialize the SessionManager:

kotlin
Copy code
package com.sanjeet.applicationsessionmanager

import android.app.Application
import com.sanjeet.sessionmanager.ActivityLifecycleCallbacksHandler
import com.sanjeet.sessionmanager.SessionManager
import com.sanjeet.sessionmanager.dataBase.DatabaseProvider
import com.sanjeet.sessionmanager.dataBase.SessionRecordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private lateinit var sessionManager: SessionManager
    private lateinit var activityLifecycleCallbacksHandler: ActivityLifecycleCallbacksHandler
    private lateinit var sessionRecordDao: SessionRecordDao

    override fun onCreate() {
        super.onCreate()

        // Initialize Room Database
        val sessionDatabase = DatabaseProvider.getDatabase(this)
        sessionRecordDao = sessionDatabase.sessionRecordDao()

        // Initialize SessionManager with optional crash handler
        sessionManager = SessionManager(
            sessionRecordDao,
            this,
            applicationScope,
            useCrashHandler = true // Set this to false if you don't want to use the crash handler
        )

        // Initialize ActivityLifecycleCallbacksHandler
        activityLifecycleCallbacksHandler = ActivityLifecycleCallbacksHandler(
            sessionManager,
            getAppScope()
        )
        registerActivityLifecycleCallbacks(activityLifecycleCallbacksHandler)
    }

    private fun getAppScope(): CoroutineScope {
        return applicationScope
    }
}
3. Retrieve Session Data
Use the provided utility functions to retrieve session data.

kotlin
Copy code
class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: TextView = findViewById(R.id.txt)
        sessionManager = (application as MyApplication).sessionManager

        btn.setOnClickListener {
            sessionManager.getSessionData(applicationScope, deleteAfterRetrieving = false) { sessions ->
                // Handle the list of sessions
            }
        }
    }
}
For retrieving session data as JSON:

kotlin
Copy code
btn.setOnClickListener {
    sessionManager.getSessionDataAsJson(applicationScope, deleteAfterRetrieving = false) { jsonArray ->
        // Handle the JSON array of sessions
    }
}
License
This project is licensed under the MIT License - see the LICENSE file for details.

Contributing
Fork it!
Create your feature branch: git checkout -b my-new-feature
Commit your changes: git commit -am 'Add some feature'
Push to the branch: git push origin my-new-feature
Submit a pull request
Acknowledgments
Thanks to the Android community for their continuous support and contributions.
