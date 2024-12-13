# Internet Listener Example

This repository provides two distinct approaches for monitoring internet connectivity in Android applications. Each approach serves different use cases and offers unique advantages, but it's important to note that the broadcast-based approach is **deprecated** starting from Android 7.0 (API level 24). This guide will help you understand the differences and make an informed decision.

---

## Class Comparison

### 1. `INetworkReceiver` (Deprecated)

- **Purpose:**  
  `INetworkReceiver` is a `BroadcastReceiver` designed to listen for system-level connectivity change events. It provides an abstract implementation that allows developers to define specific behavior when the internet becomes available or unavailable.

- **Key Features:**
  - Reacts to system broadcast intents for connectivity changes.
  - Uses `ConnectivityManager` to verify the current network's state and capabilities.
  - Lightweight and easy to implement.

- **Limitations:**
  - **Deprecated in Android 7.0 (API level 24):**  
    Starting from this version, the system no longer sends the `CONNECTIVITY_ACTION` broadcast to registered receivers when the app is in the background. This reduces its reliability for modern apps.
  - Requires manual lifecycle management.

- **Usage Scenario:**  
  Only recommended for legacy support or specific foreground tasks when the app is actively running.

---

### 2. `InternetConnectionLiveData`

- **Purpose:**  
  `InternetConnectionLiveData` is a lifecycle-aware implementation for observing internet connectivity changes in real-time. Built on `LiveData`, it integrates seamlessly with Android's architecture components.

- **Key Features:**
  - Uses `ConnectivityManager.NetworkCallback` for precise and efficient monitoring of network changes.
  - Emits `true` or `false` based on internet availability, ensuring real-time updates.
  - Automatically starts and stops monitoring based on the lifecycle of the observing component.

- **Advantages:**
  - Fully supported on modern Android versions.
  - Handles lifecycle management automatically.

- **Usage Scenario:**  
  Best suited for apps that require reliable and real-time internet monitoring in lifecycle-aware components (e.g., Activities or Fragments).

---

## Comparison Table

| Feature                              | `INetworkReceiver` (Deprecated)              | `InternetConnectionLiveData`                  |
|--------------------------------------|---------------------------------------------|-----------------------------------------------|
| **Architecture**                     | Broadcast-based                             | Lifecycle-aware LiveData                      |
| **Trigger Mechanism**                | System broadcast intents                    | `ConnectivityManager.NetworkCallback`         |
| **Real-time Updates**                | No (reacts to broadcasts only)              | Yes                                           |
| **Lifecycle Awareness**              | No                                          | Yes                                           |
| **Support on Modern Android**        | Deprecated starting from API level 24       | Fully supported                               |
| **Use Case**                         | Legacy apps or one-time actions             | Continuous monitoring in real-time            |

---

## Choosing the Right Class

- Use **`INetworkReceiver`** **only for legacy support** or when targeting devices below Android 7.0.
- Use **`InternetConnectionLiveData`** for modern applications that need reliable, real-time connectivity monitoring.

---

## Example Integration

### For `INetworkReceiver` (Legacy, use with caution):

```kotlin
class CustomNetworkReceiver : INetworkReceiver() {
    override fun onInternetAvailable() {
        Log.d("CustomNetworkReceiver", "Internet is now available")
    }

    override fun onInternetUnavailable() {
        Log.d("CustomNetworkReceiver", "Internet is now unavailable")
    }
}

// Registering the receiver
val receiver = CustomNetworkReceiver()
val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
registerReceiver(receiver, filter)
```
### For `InternetConnectionLiveData` (Preferred):

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val internetConnectionLiveData = InternetConnectionLiveData(this)
        internetConnectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                Log.d("MainActivity", "Internet is available")
            } else {
                Log.d("MainActivity", "Internet is unavailable")
            }
        }
    }
}
```
