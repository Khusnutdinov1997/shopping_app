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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopping.R
import com.example.shoppingapp.presentation.dialog_window.MainDialog
import com.example.shoppingapp.ui.theme.DarkText
import com.example.shoppingapp.ui.theme.GrayLightSoft
import com.example.shoppingapp.ui.theme.PurpleGrey40
import com.example.shoppingapp.utils.UIEvent
import kotlinx.coroutines.flow.flowOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskItemScreen(
    viewModel: TaskItemViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val itemList = (viewModel.itemList ?: flowOf(emptyList())).collectAsState(emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent){
                is UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                androidx.compose.material3.Snackbar(
                    snackbarData = snackbarData,
                    containerColor = PurpleGrey40,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
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
                        modifier = Modifier.weight(1f),
                        value = viewModel.itemText.value,
                        onValueChange = { text: String ->
                            viewModel.onEvent(TaskItemEvent.OnTextChange(text))
                        },
                        label = {
                            Text(
                                text = "New item",
                                fontSize = 12.sp
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = DarkText,
                        ),
                        singleLine = true
                    )
                    IconButton(onClick = {
                        viewModel.onEvent(TaskItemEvent.OnSaveTask)
                    }) {
                        Icon(
                            painter = painterResource(
                                R.drawable.add
                            ),
                            contentDescription = "Add"
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                items(itemList.value){ item ->
                    UITaskItem(itemTask = item, onEvent = { event ->
                        viewModel.onEvent(event)
                    })
                }
            }

            if(itemList.value.isEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    text = "Empty",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color.LightGray
                )
            }
        }
        MainDialog(viewModel)
    }
}