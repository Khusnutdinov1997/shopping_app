package com.example.shoppingapp.utils

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Locale

fun ViewModel.getCurrentTime(): String {
    val formater = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val calendar = Calendar.getInstance()
    return formater.format(calendar.time)
}