package md.absa.makeup.challenge

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import md.absa.makeup.challenge.workers.MakeUpWorker
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MakeUpApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    /**
     * Fire our worker to fetch makeup data via [setupRecurringWork]
     *
     * if for sure we knew the data would change ata some point, id use a periodic
     * work request as below
     *  val repeatingRequest = PeriodicWorkRequestBuilder<MakeUpWorker>(3, TimeUnit.HOURS).build()
     *  WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
     *  MakeUpWorker.WORK_NAME,
     *  ExistingPeriodicWorkPolicy.KEEP,
     *  repeatingRequest
     *  )
     */
    private fun setupRecurringWork() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val task = OneTimeWorkRequestBuilder<MakeUpWorker>().setConstraints(constraints).build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(task)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Stetho.initializeWithDefaults(applicationContext)
        delayedInit()
    }
}
