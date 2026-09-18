package com.example.shoppingapp.presentation.main_Screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.repository.ShoppingListRepository
import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import com.example.shoppingapp.data.task_item.TaskItem
import com.example.shoppingapp.presentation.dialog_window.DialogController
import com.example.shoppingapp.presentation.dialog_window.DialogEvent
import com.example.shoppingapp.utils.Routes
import com.example.shoppingapp.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var showFloatingButton: MutableState<Boolean> = mutableStateOf(false)
        private set

    fun updateFloatingButtonVisibility(route: String){
        showFloatingButton.value = !(route == Routes.ABOUT_SCREEN || route == Routes.SETTINGS_SCREEN)
    }

    override var dialogTitle: MutableState<String> = mutableStateOf("List name: ")
        private set
    override var editableText: MutableState<String> = mutableStateOf("")
        private set
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
        private set
    override var showEditableText: MutableState<Boolean> = mutableStateOf(true)
        private set

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }

            is DialogEvent.OnConfirm -> {
                openDialog.value = false
                editableText.value = ""

            }

            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    fun onEvent(event: MainScreenEvent){
        when (event) {
            is MainScreenEvent.OnShowDialog -> {
                openDialog.value = true
            }
            is MainScreenEvent.OnItemSave -> {
                if(editableText.value.isEmpty())return
                viewModelScope.launch {
                    shoppingListRepository.insertItem(
                        ShoppingListItem(
                            id = null,
                            name = editableText.value,
                            time = "15:12",
                            allItemsCount = 0,
                            allSelectedItemsCount = 0
                        )
                    )
                }
            }
            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UIEvent.OnNavigateMain(event.route))
            }
            is MainScreenEvent.Navigate -> {
                sendUiEvent(UIEvent.OnNavigate(event.route))
            }
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}