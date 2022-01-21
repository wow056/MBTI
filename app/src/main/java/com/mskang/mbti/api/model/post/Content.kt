package com.mskang.mbti.api.model.post

data class Content(
    val content: String,
    val date: String,
    val idx: Int,
    val is_use: Int,
    val table_division: Any,
    val target: String,
    val title: String,
    val user_uuid: String,
    val uuid: String,
    val voteyn: Any
)