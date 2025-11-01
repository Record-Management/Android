package see.day.domain.repository

interface FcmRepository {

    suspend fun getFcmToken(): Result<String>
}
