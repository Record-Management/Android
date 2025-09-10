package see.day.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import see.day.domain.usecase.login.GetLoginStateUseCase
import see.day.model.navigation.AppStartState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoginStateUseCase: GetLoginStateUseCase
) : ViewModel() {

    private val _uiEffect: MutableSharedFlow<AppStartState> = MutableSharedFlow()
    val uiEffect: SharedFlow<AppStartState> = _uiEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            getLoginStateUseCase().collect {
                _uiEffect.emit(it)
            }
        }
    }
}
