package com.example.shoppingapp.presentation.new_note_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.note_item.NoteItem
import com.example.shoppingapp.data.repository.NoteRepository
import com.example.shoppingapp.dataStore.DataStoreManager
import com.example.shoppingapp.utils.UIEvent
import com.example.shoppingapp.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val dataStoreManager: DataStoreManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var noteItem: NoteItem? = null
    var noteId: Int = -1

    var titleColor by mutableStateOf("#FFB388FF")
        private set
    var onTitleText by mutableStateOf("")
        private set

    var onDescriptionText by mutableStateOf("")
        private set

    fun onEvent(event: NewNoteEvent) {
        when (event) {
            is NewNoteEvent.OnSave -> {
                viewModelScope.launch {
                    if (onTitleText.isEmpty()) {
                        sendUiEvent(UIEvent.ShowSnackBar("Title can't be empty"))
                        return@launch
                    }
                    noteRepository.insertNote(
                        NoteItem(
                            id = noteItem?.id,
                            title = onTitleText,
                            description = onDescriptionText,
                            time = noteItem?.time ?: getCurrentTime()
                        )
                    )
                    sendUiEvent(UIEvent.PopBackStack)
                }
            }

            is NewNoteEvent.OnDescription -> {
                onDescriptionText = event.description
            }

            is NewNoteEvent.OnTitleChange -> {
                onTitleText = event.title
            }

            else -> {}
        }
    }

    fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}