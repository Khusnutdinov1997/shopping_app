package com.example.shoppingapp.utils

object ProgressHelper {
    fun getProgress(itemsCount: Int, selectedItemsCount: Int): Float{
        return if (itemsCount == 0 || selectedItemsCount == 0) 0.0f
        else selectedItemsCount.toFloat() / itemsCount
    }
}