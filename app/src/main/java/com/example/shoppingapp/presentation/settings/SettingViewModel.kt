package com.example.shoppingapp.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataStore.DataStoreManager
import com.example.shoppingapp.utils.ColorsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val colorList = mutableStateOf<List<ColorItem>>(emptyList())

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreference("#FFB388FF", DataStoreManager.TITLE_COLOR)
                .collect{ selectedColor ->
                    val tempColorList = ArrayList<ColorItem>()
                    ColorsUtils.colorsList.forEach { color ->
                        tempColorList.add(ColorItem(color = color, selected = color == selectedColor))
                    }
                    colorList.value = tempColorList
                }
        }
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.OnItemSelected -> {
                viewModelScope.launch {
                    dataStoreManager.saveStringPreference(event.color, DataStoreManager.TITLE_COLOR)
                }
            }
        }
    }

}