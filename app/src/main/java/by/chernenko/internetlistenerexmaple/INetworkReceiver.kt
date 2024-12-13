package by.chernenko.internetlistenerexmaple

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

abstract class INetworkReceiver: BroadcastReceiver() {

    // Called when a broadcast intent is received
    override fun onReceive(context: Context, intent: Intent?) {
        if (isInternetAvailable(context)) {
            onInternetAvailable() // Trigger when internet is available
        } else {
            onInternetUnavailable() // Trigger when internet is unavailable
        }
    }

    // Checks if the internet is available by evaluating active network capabilities
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false // No active network
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false // No network capabilities

        // Check if the active network has both a specific transport type and internet capability
        return when {
            // Wi-Fi network with internet access
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI).and(activeNetwork.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)) -> true
            // Cellular network with internet access
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR).and(activeNetwork.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)) -> true
            else -> false
        }
    }

    // Abstract method to handle when internet is available
    abstract fun onInternetAvailable()

    // Abstract method to handle when internet is unavailable
    abstract fun onInternetUnavailable()
}