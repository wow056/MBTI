package com.mskang.mbti.scenarios

import androidx.annotation.DrawableRes
import com.mskang.mbti.R

sealed class BottomNavigationRoute(val route: String, @DrawableRes val  unselectedRes: Int, @DrawableRes val selectedRes: Int) {
    object Board: BottomNavigationRoute("board", R.drawable.ic_home_inactive, R.drawable.ic_home_active)
    object MBTI: BottomNavigationRoute("mbti", R.drawable.ic_mbti_inactive, R.drawable.ic_mbti_active)
    object Chatting: BottomNavigationRoute("chatting", R.drawable.ic_chat_inactive, R.drawable.ic_chat_active)
    object Profile: BottomNavigationRoute("profile", R.drawable.ic_profile_inactive, R.drawable.ic_profile_active)
}
