package com.khayrul.doauto.presentation.util

import com.khayrul.doauto.R

sealed class Screen(
    val route:String,
    val title:String,
    val image: Int
) {
    object Home: Screen(
        route = "home",
        title = "Home",
        image = R.drawable.home
    )
    object AutoReply: Screen(
        route = "auto_reply",
        title = "Auto Reply",
        image = R.drawable.reply
    )
}
