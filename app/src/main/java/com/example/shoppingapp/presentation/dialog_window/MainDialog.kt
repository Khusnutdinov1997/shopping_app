package com.example.shoppingapp.presentation.dialog_window

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.ui.theme.DarkText
import com.example.shoppingapp.ui.theme.GrayLightSoft

@Composable
fun MainDialog(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        AlertDialog(
            onDismissRequest = { dialogController.onDialogEvent(event = DialogEvent.OnCancel) },// Для отмены диалога, при клики за диалог
            title = null,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dialogController.dialogTitle.value,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (dialogController.showEditableText.value) {
                        TextField(
                            value = dialogController.editableText.value,
                            onValueChange = { text ->
                                dialogController.onDialogEvent(event = DialogEvent.OnTextChange(text = text))
                            },
                            label = {
                                Text(
                                    text = "List name:"
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedLabelColor = GrayLightSoft,
                                unfocusedLabelColor = GrayLightSoft,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = DarkText,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnConfirm)
                    }
                ) {
                    Text(
                        text = "ok"
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnCancel)
                    }
                ) {
                    Text(
                        text = "cancel"
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainDialogPreview() {
    MainDialog(
        dialogController = FakeDialogController(
            dialogTitle = "List name",
            editableText = "note",
            openDialog = true,
            showEditableText = false
        )
    )
}

private class FakeDialogController(
    dialogTitle: String,
    editableText: String,
    openDialog: Boolean,
    showEditableText: Boolean
): DialogController{
    override val dialogTitle = mutableStateOf(dialogTitle)
    override val editableText = mutableStateOf(editableText)
    override val openDialog = mutableStateOf(openDialog)
    override val showEditableText = mutableStateOf(showEditableText)

    override fun onDialogEvent(event: DialogEvent) {
    }
}