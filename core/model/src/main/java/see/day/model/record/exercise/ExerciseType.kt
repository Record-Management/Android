package see.day.model.record.exercise

import kotlinx.serialization.Serializable

@Serializable
enum class ExerciseType(val displayName: String) {
    RUNNING("러닝"), GOLF("골프"), BASKETBALL("농구"), SWIMMING("수영"), BASEBALL("야구"), YOGA("요가"), WEIGHTS("웨이트 트레이닝"), CYCLING("자전거"), SOCCER("축구"), TENNIS("테니스")
}
