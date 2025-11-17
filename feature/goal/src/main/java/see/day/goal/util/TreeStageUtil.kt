package see.day.goal.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import see.day.goal.R
import see.day.model.goal.TreeStage

@StringRes
fun TreeStage.completeText() : Int{
    return when(this) {
        TreeStage.STAGE_1 -> R.string.tree_stage_1_complete_text
        TreeStage.STAGE_2 -> R.string.tree_stage_2_complete_text
        TreeStage.STAGE_3 -> R.string.tree_stage_3_complete_text
        TreeStage.STAGE_4 -> R.string.tree_stage_4_complete_text
    }
}

@DrawableRes
fun TreeStage.treeImage() : Int{
    return when(this) {
        TreeStage.STAGE_1 -> R.drawable.image_tree_stage_1
        TreeStage.STAGE_2 -> R.drawable.image_tree_stage_2
        TreeStage.STAGE_3 -> R.drawable.image_tree_stage_3
        TreeStage.STAGE_4 -> R.drawable.image_tree_stage_4
    }
}
