package com.example.shoppingapp.presentation.task_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.data.task_item.TaskItem

@Composable
fun UITaskItem(
    itemTask: TaskItem,
    onEvent: (TaskItemEvent) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable{onEvent(TaskItemEvent.OnShowEditDialog(itemTask))},
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = itemTask.name,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            )
            Checkbox(
                checked = itemTask.check,
                onCheckedChange = {onEvent(TaskItemEvent.OnCheckedChange(itemTask))}
            )
            IconButton(
                onClick = {onEvent(TaskItemEvent.OnDelete(itemTask))}
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun UiTaskItemPreview(){
    UITaskItem(
        TaskItem(
            idList = 1,
            id = 1,
            name = "Name Item",
            check = true
        ),
        onEvent = {}
    )
}