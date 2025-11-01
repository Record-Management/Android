package see.day.domain.usecase.fcm

import see.day.domain.repository.FcmRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val fcmRepository: FcmRepository
){

    suspend operator fun invoke() : Result<String> {
        return fcmRepository.getFcmToken()
    }
}
