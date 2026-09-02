package com.example.shoppingapp.presentation.task_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.repository.ShoppingListRepository
import com.example.shoppingapp.data.repository.TaskRepository
import com.example.shoppingapp.data.task_item.TaskItem
import com.example.shoppingapp.presentation.dialog_window.DialogController
import com.example.shoppingapp.presentation.dialog_window.DialogEvent
import com.example.shoppingapp.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskItemViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val shoppingListRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(), DialogController
{
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var taskItem: TaskItem? = null

    override var dialogTitle: MutableState<String> = mutableStateOf("Edit name: ")
        private set
    override var editableText: MutableState<String> = mutableStateOf("")
        private set
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
        private set
    override var showEditableText: MutableState<Boolean> = mutableStateOf(true)
        private set

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
            is DialogEvent.OnConfirm -> {
                taskItem = taskItem?.copy(name = editableText.value)


                editableText.value = ""
                openDialog.value = false
            }
        }
    }

    private fun sendUiEvent(event: UIEvent.ShowSnackBar){
        viewModelScope.launch {
            _uiEvent.send(event) // send - нужна чтобы передать event в channel
        }
    }
}