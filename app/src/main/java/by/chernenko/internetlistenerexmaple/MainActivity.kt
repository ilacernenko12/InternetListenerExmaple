package by.chernenko.internetlistenerexmaple

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize InternetConnectionLiveData
        val internetConnectionLiveData = InternetConnectionLiveData(this)

        // Observe the LiveData to monitor internet connectivity
        internetConnectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                // do something when Internet available
            } else {
                // do something when Internet unavailable
            }
        }

        val networkReceiver = object : INetworkReceiver() {
            override fun onInternetAvailable() {
                // do something when Internet available
            }

            override fun onInternetUnavailable() {
                // do something when Internet unavailable
            }   
        }
    }
}