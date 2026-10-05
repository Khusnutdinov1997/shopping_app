package com.example.shoppingapp.presentation.note_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.note_item.NoteItem
import com.example.shoppingapp.data.repository.NoteRepository
import com.example.shoppingapp.presentation.dialog_window.DialogController
import com.example.shoppingapp.presentation.dialog_window.DialogEvent
import com.example.shoppingapp.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel(), DialogController{

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle: MutableState<String> = mutableStateOf("List name: ")
        private set
    override var editableText: MutableState<String> = mutableStateOf("")
        private set
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
        private set
    override var showEditableText: MutableState<Boolean> = mutableStateOf(false)
        private set

    private var noteItem: NoteItem? = null

    private var originalNoteList = listOf<NoteItem>()

    private var noteItems by mutableStateOf(listOf<NoteItem>())

    val noteListFlow = noteRepository.getAllNotes()

    var searchText by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            noteListFlow.collect { list ->
                noteItems = list
                originalNoteList = list
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                viewModelScope.launch {
                    noteRepository.deleteNote(noteItem!!)
                    sendUiEvent(UIEvent.ShowSnackBar("Undone delete note?"))
                }
                openDialog.value = false
            }
            else -> {}
        }
    }

    fun onEvent(event: NoteListEvent){
        when(event){
            is NoteListEvent.onItemClick -> {
                sendUiEvent(UIEvent.OnNavigate(event.route))
            }
            is NoteListEvent.onShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }
            is NoteListEvent.undoneDeleteItem -> {
                viewModelScope.launch {
                    noteRepository.insertNote(noteItem!!)
                }
            }
            is NoteListEvent.onTextSearchChange -> {
                searchText = event.text
                noteItems = originalNoteList.filter { note ->
                    note.title.lowercase().startsWith(searchText.lowercase())
                }
            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UIEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}