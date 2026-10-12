package com.example.shoppingapp.presentation.note_screen.companent

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.data.note_item.NoteItem
import com.example.shoppingapp.presentation.note_screen.NoteListEvent
import com.example.shoppingapp.ui.theme.DarkText
import com.example.shoppingapp.ui.theme.Pink80
import com.example.shoppingapp.utils.Routes

@Composable
fun NoteItem(
    item: NoteItem,
    onEvent: (NoteListEvent) -> Unit,
    titleColor: String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, top = 3.dp, end = 3.dp)
            .clickable{onEvent(NoteListEvent.onItemClick(Routes.NEW_NOTE_SCREEN + "/${item.id}"))}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp, start = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    text = item.title,
                    color = Color(parseColor(titleColor))
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp, end = 10.dp),
                    text = item.time,
                    fontSize = 12.sp,
                    color = DarkText
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 5.dp, start = 10.dp, bottom = 10.dp),
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    onClick = {onEvent(NoteListEvent.onShowDeleteDialog(item))}
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Pink80
                    )
                }
            }
        }
    }
}