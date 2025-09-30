package see.day.domain.repository

import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete

interface UserRepository {
    suspend fun onboardComplete(onboardingComplete: OnboardingComplete) : Result<Unit>
    suspend fun getMainRecordType() : Result<RecordType>
    suspend fun deleteUser() : Result<Unit>
}
