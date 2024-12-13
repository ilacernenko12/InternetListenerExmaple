package by.chernenko.internetlistenerexmaple

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

class InternetConnectionLiveData(context: Context) : LiveData<Boolean>() {

    // ConnectivityManager for monitoring network connectivity
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // NetworkRequest to filter networks with Wi-Fi or Cellular transport and internet capability
    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI) // Add Wi-Fi transport
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR) // Add Cellular transport
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) // Require internet capability
        .build()

    // NetworkCallback to monitor network changes
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        // Called when a network becomes available
        override fun onAvailable(network : Network) {
            Log.e("InternetConnectionLiveData", "The default network is now: $network")
            postValue(true)
        }

        // Called when a network is lost
        override fun onLost(network : Network) {
            Log.e("InternetConnectionLiveData","The application no longer has a default network. The last default network was $network")
            postValue(false)
        }

        // Called when a network is about to be lost (not frequently used)
        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            // Optionally handle the network losing event
        }

        // Called when the capabilities of an existing network change
        override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
            val isConnected: Boolean = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            Log.e("InternetConnectionLiveData", "Network capabilities changed: $isConnected")
            postValue(isConnected)
        }
    }

    // Start monitoring network changes when the LiveData becomes active
    override fun onActive() {
        super.onActive()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    // Stop monitoring network changes when the LiveData becomes inactive
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}