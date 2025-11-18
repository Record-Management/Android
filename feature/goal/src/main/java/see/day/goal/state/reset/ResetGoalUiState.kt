package see.day.goal.state.reset

import see.day.model.record.RecordType

data class ResetGoalUiState(
    val step: GoalResetStep,
    val recordType: RecordType,
    val goalDays: Int
) {
    companion object {
        val init = ResetGoalUiState(GoalResetStep.RECORD,RecordType.HABIT, goalDays = 10)
    }
}

enum class GoalResetStep(val titleRes: Int, val iconRes: Int) {
    RECORD(see.day.ui.R.string.record_message, see.day.ui.R.drawable.onboard_record),
    DAY(see.day.ui.R.string.goals_message, see.day.ui.R.drawable.onboard_goal)
}

fun GoalResetStep.getProgress() : Float {
    val totalSteps = GoalResetStep.entries.size
    return (this.ordinal + 1).toFloat() / totalSteps
}
