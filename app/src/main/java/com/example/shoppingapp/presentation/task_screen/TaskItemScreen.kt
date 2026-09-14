package com.example.shoppingapp.presentation.task_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.presentation.dialog_window.MainDialog
import com.example.shoppingapp.ui.theme.GrayLight
import com.example.shoppingapp.ui.theme.GrayLightSoft
import com.example.shoppingapp.utils.UIEvent
import kotlinx.coroutines.flow.flowOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskItemScreen(
    taskItemViewModel: TaskItemViewModel = hiltViewModel()
) {
    val scaffoldState = remember { SnackbarHostState() }

    val itemList = (taskItemViewModel.itemList ?: flowOf(emptyList())).collectAsState(emptyList())

    LaunchedEffect(
        true
    ) {
        taskItemViewModel.uiEvent.collect{uIEvent ->
            when(uIEvent){
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(uIEvent.message)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(scaffoldState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(GrayLightSoft)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f),
                        value = taskItemViewModel.itemText.value,
                        onValueChange = { taskItemViewModel.onEvent(TaskItemEvent.OnTextChange(it)) },
                        label = {
                            Text(
                                text = "New text",
                                fontSize = 12.sp
                            )
                        },
                        singleLine = true
                    )

                    IconButton(
                        onClick = { taskItemViewModel.onEvent(TaskItemEvent.OnSaveTask) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }

                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, end = 5.dp)
        ) {
            items(itemList.value) { task ->
                UITaskItem(
                    itemTask = task,
                    onEvent = { event ->
                        taskItemViewModel.onEvent(event)
                    }
                )
            }
        }
        MainDialog(
            dialogController = taskItemViewModel
        )
        if (itemList?.value?.isEmpty() == true) {
            Text(
                text = "Empty",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                color = GrayLight
            )
        }
    }
}