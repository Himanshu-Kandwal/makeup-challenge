package md.absa.makeup.challenge.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import md.absa.makeup.challenge.R
import md.absa.makeup.challenge.databinding.ActivityMainBinding
import md.absa.makeup.challenge.ui.viewmodels.StoreViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var nightModeActive = false

    private val storeViewModel by viewModels<StoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?
        navHostFragment?.let {
            val navController = it.navController

            appBarConfiguration = AppBarConfiguration.Builder(
                R.id.brandsFragment,
                R.id.productFragment,
                R.id.productsFragment,
                R.id.webViewFragment,
            ).build()

            setupActionBarWithNavController(navController, appBarConfiguration)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splashFragment -> {
                        binding.toolbar.visibility = View.GONE
                    }
                    R.id.welcomeFragment -> {
                        binding.toolbar.visibility = View.GONE
                    }
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
        storeViewModel.darkThemeEnabled.observe(this) { nightModeActive ->
            this.nightModeActive = nightModeActive
            val defaultMode = if (nightModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(defaultMode)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.dayNightMode) {
            storeViewModel.toggleNightMode()
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (nightModeActive) {
            menu?.findItem(R.id.dayNightMode)?.setIcon(R.drawable.icn_light_mode)
        } else {
            menu?.findItem(R.id.dayNightMode)?.setIcon(R.drawable.icn_night_mode)
        }
        return true
    }
}
