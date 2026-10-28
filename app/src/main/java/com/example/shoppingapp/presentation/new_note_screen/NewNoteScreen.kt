package com.example.shoppingapp.presentation.new_note_screen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.ui.theme.PurpleGrey40
import com.example.shoppingapp.utils.UIEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { uIEvent ->
            when (uIEvent) {
                is UIEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uIEvent.message
                    )
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
                    modifier = Modifier.padding(bottom = 24.dp),
                    backgroundColor = PurpleGrey40,
                    snackbarData = data
                )
            }
        }
    ) {
        NewNoteContent(
            onSave = { viewModel.onEvent(NewNoteEvent.OnSave) },
            onValueChangeTitle = { text ->
                viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
            },
            title = viewModel.onTitleText,
            onValueChangeDescription = { text ->
                viewModel.onEvent(NewNoteEvent.OnDescription(text))
            },
            description = viewModel.onDescriptionText,
            textColor = Color(parseColor(viewModel.titleColor))
        )
    }
}

@Composable
fun NewNoteContent(
    onSave: () -> Unit,
    onValueChangeTitle: (String) -> Unit,
    title: String,
    onValueChangeDescription: (String) -> Unit,
    description: String,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    maxLines = 1,
                    textStyle = TextStyle(color = textColor),
                    label = {
                        Text(
                            text = "Title...",
                            color = Color.Black
                        )
                    },
                    onValueChange = onValueChangeTitle,
                    value = title,
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    )
                )

                IconButton(
                    onClick = onSave,
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null
                    )
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = {
                    Text(
                        text = "Description...",
                        color = Color.Black
                    )
                },
                onValueChange = onValueChangeDescription,
                value = description,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewNotePreview() {
    NewNoteContent(
        onSave = {},
        onValueChangeTitle = {},
        title = "hello",
        onValueChangeDescription = {},
        description = "des",
        textColor = Color.Black
    )
}