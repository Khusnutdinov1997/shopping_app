package com.example.shoppingapp.presentation.task_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.repository.ShoppingListRepository
import com.example.shoppingapp.data.repository.TaskRepository
import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import com.example.shoppingapp.data.task_item.TaskItem
import com.example.shoppingapp.presentation.dialog_window.DialogController
import com.example.shoppingapp.presentation.dialog_window.DialogEvent
import com.example.shoppingapp.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskItemViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val shoppingListRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var taskItem: TaskItem? = null

    var shoppingListItem: ShoppingListItem? = null

    var listId: Int = -1

    var itemList: Flow<List<TaskItem>>? = null

    var itemText: MutableState<String> = mutableStateOf("")
        private set

    override var dialogTitle: MutableState<String> = mutableStateOf("Edit name: ")
        private set
    override var editableText: MutableState<String> = mutableStateOf("")
        private set
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
        private set
    override var showEditableText: MutableState<Boolean> = mutableStateOf(true)
        private set

    init {
        listId = savedStateHandle.get<String>("listId")?.toInt()!!
        itemList = taskRepository.getTasksByListId(listId)
        viewModelScope.launch {
            shoppingListItem = shoppingListRepository.getShoppingListItemById(listId)
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }

            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }

            is DialogEvent.OnConfirm -> {
                taskItem = taskItem?.copy(name = editableText.value)

                onEvent(TaskItemEvent.OnSaveTask)

                editableText.value = ""
                openDialog.value = false
            }
        }
    }

    fun onEvent(event: TaskItemEvent) {
        when (event) {
            is TaskItemEvent.OnSaveTask -> {
                viewModelScope.launch {
                    if (listId == -1) return@launch
                    if (taskItem != null) {
                        if (taskItem!!.name.isEmpty()) {
                            sendUiEvent(UIEvent.ShowSnackBar("name must not be empty"))
                            return@launch
                        } else {
                            if (itemText.value.isEmpty()) {
                                sendUiEvent(UIEvent.ShowSnackBar("name must not be empty"))
                                return@launch
                            }
                        }
                        taskRepository.insertTask(
                            TaskItem(
                                id = taskItem?.id,
                                idList = listId,
                                name = taskItem?.name ?: itemText.value,
                                check = taskItem?.check ?: false
                            )
                        )
                        itemText.value = ""
                        taskItem = null
                    }
                }
                viewModelScope.launch {
                    updateShoppingListCount()
                }
            }

            is TaskItemEvent.OnShowEditDialog -> {
                taskItem = event.item
                openDialog.value = true
                editableText.value = taskItem?.name ?: ""
            }

            is TaskItemEvent.OnTextChange -> {
                itemText.value = event.text
            }

            is TaskItemEvent.OnDelete -> {
                viewModelScope.launch {
                    taskRepository.deleteTask(event.item)
                }
                viewModelScope.launch {
                    updateShoppingListCount()
                }
            }

            is TaskItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    taskRepository.updateTask(event.item)
                }
                viewModelScope.launch {
                    updateShoppingListCount()
                }
            }
        }
    }

    private suspend fun updateShoppingListCount(isTextChange: Boolean = false) {
        itemList?.let { flow ->
            val list = flow.first()
            var count = 0
            list.forEach { item ->
                if (item.check) count++
            }
            val updateItem =
                shoppingListItem?.copy(allItemsCount = list.size, allSelectedItemsCount = count)
            updateItem?.let { item ->
                shoppingListRepository.update(item)
                shoppingListItem = item
            }
            if (isTextChange) {
                val newSelectedCount = list.count { item ->
                    item.check
                }
                val oldSelectedCount = shoppingListItem?.allSelectedItemsCount ?: 0
                val increment = newSelectedCount - oldSelectedCount
                shoppingListRepository.updateShoppingListCount(
                    shoppingListItem?.id ?: listId,
                    increment
                )
            }
        }
    }

    private fun sendUiEvent(event: UIEvent.ShowSnackBar) {
        viewModelScope.launch {
            _uiEvent.send(event) // send - нужна чтобы передать event в channel
        }
    }
}