package com.example.shoppingapp.utils

import android.os.Message

sealed class UIEvent {
    object PopBackStack : UIEvent()
    data class OnNavigate(
        val route: String
    ) : UIEvent()

    data class ShowSnackBar(
        val message: String
    ) : UIEvent()

    data class OnNavigateMain(
        val route: String
    ) : UIEvent()
}