package com.example.shoppingapp.presentation.note_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.presentation.dialog_window.MainDialog
import com.example.shoppingapp.presentation.note_screen.companent.NoteItem
import com.example.shoppingapp.ui.theme.GrayLightSoft
import com.example.shoppingapp.ui.theme.PurpleGrey40
import com.example.shoppingapp.utils.UIEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(true) {
        viewModel.uiEvent.collect { uIEvent ->
            when (uIEvent) {
                is UIEvent.OnNavigate -> {
                    onNavigate(uIEvent.route)
                }

                is UIEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        uIEvent.message,
                        actionLabel = "undone"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.undoneDeleteItem)
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = PurpleGrey40,
                    modifier = Modifier.padding(bottom = 50.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLightSoft)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = viewModel.searchText,
                    onValueChange = { text ->
                        viewModel.onEvent(NoteListEvent.onTextSearchChange(text))
                    },
                    label = {
                        Text(
                            text = "Search..."
                        )
                    }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(viewModel.noteItems) { item ->
                    NoteItem(
                        item,
                        viewModel::onEvent,
                        titleColor = viewModel.colors
                    )
                }
            }
            MainDialog(viewModel)
            if (viewModel.noteItems.isEmpty()){
                Text(
                    text = "Empty",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    fontSize =24.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}