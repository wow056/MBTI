package com.mskang.mbti.scenarios.board

data class BoardListItem(
    val id: String,
    val title: String,
    val content: String,
    val mbti: String,
    val nickname: String,
    val likes: Int,
    val comments: Int,
)
