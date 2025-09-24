package see.day.daily.state

import see.day.daily.navigation.DailyDetail
import see.day.designsystem.util.DailyEmotion

sealed interface DailyDetailUiEvent {

    data object OnPopHome : DailyDetailUiEvent

    data class OnChangeDailyEmotion(val emotion: DailyEmotion) : DailyDetailUiEvent

    data class OnChangedText(val text: String) : DailyDetailUiEvent

    data class OnAddPhotos(val photos: List<String>) : DailyDetailUiEvent

    data class OnRemovePhoto(val photo: String) : DailyDetailUiEvent

    data object OnSaveRecord : DailyDetailUiEvent

}
