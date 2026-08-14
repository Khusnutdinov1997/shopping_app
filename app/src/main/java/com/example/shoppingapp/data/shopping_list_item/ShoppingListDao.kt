package com.example.shoppingapp.data.shopping_list_item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(shoppingListItem: ShoppingListItem)

    @Delete
    suspend fun deleteItem(shoppingListItem: ShoppingListItem)

    @Query("DELETE FROM tasks WHERE idList = :idList")
    suspend fun deleteTaskItems(idList: Int)

    @Update
    suspend fun update(shoppingListItem: ShoppingListItem)

    // гарантирует выполнение всех операций внутри метода, в рамках одной транзакции к базе, либо все выполняются, либо нет
    @Transaction
    suspend fun deleteShoppingList(shoppingListItem: ShoppingListItem){
        deleteItem(shoppingListItem)
        deleteTaskItems(shoppingListItem.id)
    }

    @Query("SELECT * FROM shoppinglistitem")
    fun getAllItems(): Flow<List<ShoppingListItem>>

    @Query("SELECT * FROM shoppinglistitem WHERE id = :idList")
    suspend fun getShoppingListItemById(idList:Int): ShoppingListItem

    @Query("UPDATE shoppinglistitem SET allItemsCount = allItemsCount + 1, allSelectedItemsCount = allSelectedItemsCount + :increment WHERE id = :idList")
    suspend fun updateShoppingListCount(idList: Int, increment: Int)
}