package see.day.goal.state.reset

import see.day.goal.R
import see.day.model.record.RecordType

data class ResetGoalUiState(
    val step: GoalResetStep,
    val recordType: RecordType?,
    val goalDays: Int
) {
    companion object {
        val init = ResetGoalUiState(GoalResetStep.RECORD, recordType = null, goalDays = 0)
    }
}

enum class GoalResetStep(val titleRes: Int, val iconRes: Int) {
    RECORD(R.string.reset_goal_record_message, see.day.ui.R.drawable.onboard_record),
    DAY(R.string.reset_goal_days_message, see.day.ui.R.drawable.onboard_goal)
}

fun GoalResetStep.getProgress() : Float {
    val totalSteps = GoalResetStep.entries.size
    return (this.ordinal + 1).toFloat() / totalSteps
}
