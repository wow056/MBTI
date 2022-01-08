package com.mskang.mbti.api.model.posts

data class PostsMBTIItem(
    val uuid: String,
    val title: String,
    val content: String?,
    val userMBTI: String?,
    val userId: String?,
    val userName: String?,
    val comment: Int,
    val like: Int
)
