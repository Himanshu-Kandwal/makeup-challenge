package md.absa.makeup.challenge.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat

object Utils {

    /**
     * Check connectivity
     */
    fun checkInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    /**
     * Coloring the status bar
     */
    fun setStatusBarColor(window: Window, color: Int, fitWindow: Boolean) {
        window.apply {
            when {
                Build.VERSION.SDK_INT in 21..29 -> {
                    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
                Build.VERSION.SDK_INT >= 30 -> {
                    // Make the status bar overlap with the activity
                    WindowCompat.setDecorFitsSystemWindows(window, fitWindow)
                }
                else -> {
                    decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
            }
            statusBarColor = color
        }
    }
}
