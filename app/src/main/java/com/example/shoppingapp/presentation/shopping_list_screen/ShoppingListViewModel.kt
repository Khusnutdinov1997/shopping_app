package com.example.shoppingapp.presentation.shopping_list_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.repository.ShoppingListRepository
import com.example.shoppingapp.presentation.dialog_window.DialogController
import com.example.shoppingapp.presentation.dialog_window.DialogEvent
import com.example.shoppingapp.utils.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(
    shoppingRepository: ShoppingListRepository
): ViewModel(), DialogController{

    val list = shoppingRepository.getAllItems()

    private val _uiEvent = Channel<UIEvent>() // приватный канал для UI events
    val uiEvent = _uiEvent.receiveAsFlow() // превращает канал в поток, на который можно подписаться



    override var dialogTitle: MutableState<String> = mutableStateOf("List name")
        private set
    override var editableText: MutableState<String> = mutableStateOf("")
        private set
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
        private set
    override var showEditableText: MutableState<Boolean> = mutableStateOf(false)
        private set





    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
            is DialogEvent.OnConfirm -> {
                //ToDo
            }
        }

    }

    private fun sendUiEvent(event: UIEvent.OnNavigate){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}