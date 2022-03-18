package md.absa.makeup.challenge.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import md.absa.makeup.challenge.data.repository.MakeUpRepository
import timber.log.Timber
import java.lang.Exception

@HiltWorker
class MakeUpWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val makeUpRepository: MakeUpRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            makeUpRepository.fetchMakeUp()
            Timber.tag(TAG).d("Success fetching makeup data")
            Result.success()
        } catch (e: Exception) {
            Timber.tag(TAG).e("Error fetching makeup data")
            Result.failure()
        }
    }
    companion object {
        private const val TAG = "MakeUpWorker"
    }
}
