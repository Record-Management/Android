package see.day.domain.usecase.record.habit

import see.day.domain.repository.RecordRepository
import see.day.domain.repository.UserRepository
import see.day.model.calendar.HabitRecordDetail
import see.day.model.exception.NoDataException
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitRecordUiModel
import javax.inject.Inject

class GetHabitRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(recordId: String): Result<HabitRecordUiModel> {
        val habitRecord = recordRepository.getRecord(recordId).getOrElse {
            return Result.failure(it)
        } as? HabitRecordDetail ?: return Result.failure(NoDataException())

        val isMainRecord = habitRecord.isMainRecord

        val canBeMain = if (isMainRecord) {
            false
        } else {
            val mainRecordType = userRepository.getMainRecordType()
                .getOrElse { return Result.failure(it) }
            mainRecordType == RecordType.HABIT
        }

        val (hour, minute) = habitRecord.notificationTime.split(":").map { it.toInt() }

        return Result.success(
            HabitRecordUiModel(
                id = habitRecord.id,
                habitType = habitRecord.habitType,
                notificationEnabled = habitRecord.notificationEnabled,
                notificationHour = hour,
                notificationMinute = minute,
                memo = habitRecord.memo,
                isMainRecord = isMainRecord,
                canBeMain = canBeMain
            )
        )
    }

}
