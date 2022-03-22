package md.absa.makeup.challenge.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import md.absa.makeup.challenge.R
import md.absa.makeup.challenge.common.Utils
import md.absa.makeup.challenge.databinding.FragmentSplashBinding
import md.absa.makeup.challenge.workers.MakeUpWorker

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Color status bar
        Utils.setStatusBarColor(requireActivity().window, Color.TRANSPARENT, false)
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Todo check if data is in the DB, if not call scheduleWork
        applicationScope.launch {
            scheduleWork()
        }
        findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
    }

    /**
     * Fire our worker to fetch makeup data via [scheduleWork]
     *
     * if for sure we knew the data would change at some point, i'd use a periodic
     * work request as below
     *  val repeatingRequest = PeriodicWorkRequestBuilder<MakeUpWorker>(1, TimeUnit.HOURS).build()
     *  WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
     *  MakeUpWorker.WORK_NAME,
     *  ExistingPeriodicWorkPolicy.KEEP,
     *  repeatingRequest
     *  )
     */
    private fun scheduleWork() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val task = OneTimeWorkRequestBuilder<MakeUpWorker>().setConstraints(constraints).build()
        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueue(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
