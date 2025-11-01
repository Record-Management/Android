package see.day.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import see.day.domain.usecase.user.UpdateFcmTokenUseCase
import timber.log.Timber

@AndroidEntryPoint
class SeedayFirebaseMessagingService(
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase
) : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Timber.i("newToken : $token")
        CoroutineScope(Dispatchers.IO).launch {
            updateFcmTokenUseCase(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Timber.i("message : $message")
    }
}
