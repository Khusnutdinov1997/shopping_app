package com.example.shoppingapp.presentation.shopping_list_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.repository.ShoppingListRepository
import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import com.example.shoppingapp.presentation.dialog_window.DialogController
import com.example.shoppingapp.presentation.dialog_window.DialogEvent
import com.example.shoppingapp.utils.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(
   val shoppingRepository: ShoppingListRepository
): ViewModel(), DialogController{

    val list = shoppingRepository.getAllItems()

    private val _uiEvent = Channel<UIEvent>() // приватный канал для UI events
    val uiEvent = _uiEvent.receiveAsFlow() // превращает канал в поток, на который можно подписаться

    private var listItem: ShoppingListItem? = null

    override var dialogTitle: MutableState<String> = mutableStateOf("List name")
        private set
    override var editableText: MutableState<String> = mutableStateOf("")
        private set
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
        private set
    override var showEditableText: MutableState<Boolean> = mutableStateOf(false)
        private set

    fun onEvent(shoppingListEvent: ShoppingListEvent) {
        when (shoppingListEvent) {
            is ShoppingListEvent.OnItemSave -> {
                if (editableText.value.isBlank()) return
                viewModelScope.launch {
                    shoppingRepository.insertItem(
                        ShoppingListItem(
                            id = listItem?.id,
                            name = editableText.value,
                            time ="25.08.26 14:40",
                            allItemsCount = listItem?.allItemsCount ?: 0,
                            allSelectedItemsCount = listItem?.allSelectedItemsCount ?: 0
                        )
                    )
                }
            }
            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(UIEvent.OnNavigate(route = shoppingListEvent.route))
            }
            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = shoppingListEvent.shoppingListItem
                openDialog.value = true
                showEditableText.value = true
                editableText.value = listItem?.name ?: ""
                dialogTitle.value = "enter name: "
            }
            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = shoppingListEvent.shoppingListItem
                openDialog.value = true
                showEditableText.value = false
                dialogTitle.value = "delete item ?"
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value){
                    onEvent(ShoppingListEvent.OnItemSave)
                }else{
                    viewModelScope.launch {
                        listItem?.let { item ->
                            shoppingRepository.deleteItem(item)
                        }
                    }
                }
                openDialog.value = false
            }
        }

    }

    private fun sendUiEvent(event: UIEvent.OnNavigate){
        viewModelScope.launch {
            _uiEvent.send(event) // send - нужна чтобы передать event в channel
        }
    }
}