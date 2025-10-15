package see.day.domain.repository

import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete
import see.day.model.user.User

interface UserRepository {
    suspend fun onboardComplete(onboardingComplete: OnboardingComplete): Result<Unit>
    suspend fun getMainRecordType(): Result<RecordType>
    suspend fun getUser(): Result<User>
    suspend fun deleteUser(): Result<Unit>
}
