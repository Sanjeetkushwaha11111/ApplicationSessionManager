package com.sanjeet.applicationsessionmanagerimport android.app.Applicationimport com.sanjeet.sessionmanager.ActivityLifecycleCallbacksHandlerimport com.sanjeet.sessionmanager.SessionManagerimport com.sanjeet.sessionmanager.dataBase.DatabaseProviderimport com.sanjeet.sessionmanager.dataBase.SessionRecordDaoimport kotlinx.coroutines.CoroutineScopeimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.SupervisorJobclass MyApplication : Application() {    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)    lateinit var sessionManager: SessionManager    private lateinit var activityLifecycleCallbacksHandler: ActivityLifecycleCallbacksHandler    private lateinit var sessionRecordDao: SessionRecordDao    override fun onCreate() {        super.onCreate()        // Initialize Room Database        val sessionDatabase = DatabaseProvider.getDatabase(this)        sessionRecordDao = sessionDatabase.sessionRecordDao()        // Initialize SessionManager        sessionManager = SessionManager(sessionRecordDao, this, applicationScope, true)        // Initialize ActivityLifecycleCallbacksHandler        activityLifecycleCallbacksHandler = ActivityLifecycleCallbacksHandler(            sessionManager,            getAppScope()        )        registerActivityLifecycleCallbacks(activityLifecycleCallbacksHandler)    }    private fun getAppScope(): CoroutineScope {        return applicationScope    }}