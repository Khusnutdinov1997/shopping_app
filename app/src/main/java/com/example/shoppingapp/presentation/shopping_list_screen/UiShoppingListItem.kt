package com.example.shoppingapp.presentation.shopping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.shopping.R
import com.example.shoppingapp.data.shopping_list_item.ShoppingListItem
import com.example.shoppingapp.ui.theme.BluePastel
import com.example.shoppingapp.ui.theme.DarkText
import com.example.shoppingapp.ui.theme.Lavender
import com.example.shoppingapp.ui.theme.LightText
import com.example.shoppingapp.ui.theme.PinkPastel
import com.example.shoppingapp.utils.Routes


@Composable
fun UiShoppingListItem(
    shoppingListItem: ShoppingListItem,
    onEvent: (ShoppingListEvent) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 3.dp, top = 18.dp, end = 3.dp)
    ) {
        val (card, deleteButton, editButton, counter) = createRefs() // ссылки для привязки области
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onEvent(ShoppingListEvent.OnItemClick(Routes.TASK_ITEM_SCREEN + "/${shoppingListItem.id}"))
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = shoppingListItem.name,
                    style = TextStyle(
                        color = DarkText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = shoppingListItem.time,
                    style = TextStyle(
                        color = LightText,
                        fontSize = 12.sp
                    )
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    progress = 0.5f
                )
            }
        }

        IconButton(
            onClick = {
                onEvent(ShoppingListEvent.OnShowDeleteDialog(shoppingListItem))
            },
            modifier = Modifier
                .constrainAs(deleteButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(card.end)
                }
                .padding(end = 10.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.outline_delete_24
                ),
                contentDescription = "Delete",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(PinkPastel)
                    .padding(5.dp),
                tint = Color.White

            )
        }

        IconButton(
            onClick = {
                onEvent(ShoppingListEvent.OnShowEditDialog(shoppingListItem))
            },
            modifier = Modifier
                .constrainAs(editButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(deleteButton.start)
                }
                .padding(end = 5.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.outline_edit_24
                ),
                contentDescription = "Edit",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BluePastel)
                    .padding(5.dp),
                tint = Color.White

            )
        }

        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .padding(5.dp)
                .constrainAs(counter) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(editButton.start)
                }
        ) {
            Text(
                text = "${shoppingListItem.allSelectedItemsCount}/${shoppingListItem.allItemsCount}",
                modifier = Modifier
                    .background(Lavender)
                    .padding(top = 3.dp, bottom = 3.dp, start = 5.dp, end = 5.dp),
                color = Color.White
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UiShoppingListPreview() {
    UiShoppingListItem(
        shoppingListItem = ShoppingListItem(
            id = 1,
            name = "list",
            time = "23.12.26",
            allItemsCount = 4,
            allSelectedItemsCount = 2
        ),
        onEvent = {}
    )
}