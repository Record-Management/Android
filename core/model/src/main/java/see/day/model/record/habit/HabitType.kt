package see.day.model.record.habit

import kotlinx.serialization.Serializable

@Serializable
enum class HabitType(val displayName: String) {
    WATER_DRINKING("물 마시기"), WALKING("산책"), READING("독서"), SAVING("저축"), TAKE_MEDICINE("약 챙겨먹기"), EARLY_RISING("일찍 일어나기"), STRETCHING("스트레칭"), EXERCISE("운동"), NO_DRINKING("금주"), NO_SMOKING("금연")
}
