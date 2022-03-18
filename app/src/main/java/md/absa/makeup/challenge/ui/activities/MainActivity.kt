package md.absa.makeup.challenge.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import md.absa.makeup.challenge.R
import md.absa.makeup.challenge.databinding.ActivityMainBinding
import md.absa.makeup.challenge.workers.MakeUpWorker

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.brandsFragment,
            R.id.productsFragment,
            R.id.productFragment,
            R.id.webViewFragment,
        ).build()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?
        navHostFragment?.let {
            val navController = it.navController
            setupActionBarWithNavController(navController, appBarConfiguration)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.brandsFragment -> {
                        binding.toolbar.setTitle(R.string.brands)
                        binding.toolbar.visibility = View.VISIBLE
                    }
                    R.id.productsFragment -> {
                        binding.toolbar.setTitle(R.string.products)
                        binding.toolbar.visibility = View.VISIBLE
                    }
                    R.id.productFragment -> {
                        binding.toolbar.setTitle(R.string.product)
                        binding.toolbar.visibility = View.VISIBLE
                    }
                    R.id.webViewFragment -> {
                        binding.toolbar.setTitle(R.string.visit_shop)
                        binding.toolbar.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.toolbar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
