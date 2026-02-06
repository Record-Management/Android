package see.day.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.domain.usecase.user.UpdateShownTutorialUseCase
import see.day.home.state.tutorial.TutorialUiEffect
import see.day.home.state.tutorial.TutorialUiEvent
import see.day.home.state.tutorial.TutorialUiState
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val updateShownTutorialUseCase: UpdateShownTutorialUseCase
) : ViewModel(){

    private val _uiState: MutableStateFlow<TutorialUiState> = MutableStateFlow(TutorialUiState.Tutorial)
    val uiState: StateFlow<TutorialUiState> = _uiState

    private val _uiEffect: MutableSharedFlow<TutorialUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<TutorialUiEffect> = _uiEffect

    fun onAction(uiEvent: TutorialUiEvent) {
        when (uiEvent) {
            is TutorialUiEvent.OnClickCloseButton -> {
                onClickBackButton()
            }
        }
    }

    private fun onClickBackButton() {
        viewModelScope.launch {
            _uiState.update { TutorialUiState.Loading }
            delay(300L)
            // 배포 전 활성화
//            updateShownTutorialUseCase()
            _uiEffect.emit(TutorialUiEffect.NavigateToHome)
        }

    }
}
