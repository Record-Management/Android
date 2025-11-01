package see.day.repository

import see.day.domain.repository.FcmRepository
import see.day.network.fcm.FcmTokenProvider
import see.day.utils.ErrorUtils.createResult
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val fcmTokenProvider: FcmTokenProvider
) : FcmRepository {

    override suspend fun getFcmToken(): Result<String> {
        return createResult {
            fcmTokenProvider.getToken()
        }
    }
}
