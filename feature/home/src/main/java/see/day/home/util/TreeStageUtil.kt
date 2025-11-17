package see.day.home.util

import androidx.annotation.DrawableRes
import see.day.home.R
import see.day.model.goal.TreeStage

@DrawableRes
internal fun TreeStage?.completeImage() : Int{
    return when(this) {
        TreeStage.STAGE_1 -> {
            R.drawable.image_tree_complete_1
        }
        TreeStage.STAGE_2 -> {
            R.drawable.image_tree_complete_2
        }
        TreeStage.STAGE_3 -> {
            R.drawable.image_tree_complete_3
        }
        TreeStage.STAGE_4 -> {
            R.drawable.image_tree_complete_4
        }
        else -> {
            R.drawable.image_tree_complete_none
        }
    }
}
