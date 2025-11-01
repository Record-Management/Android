package see.day.network.fcm

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FcmTokenProvider {
    suspend fun getToken() : String
}

class FcmTokenProviderImpl @Inject constructor() : FcmTokenProvider {
    override suspend fun getToken(): String {
        return FirebaseMessaging.getInstance().token.await()
    }
}
