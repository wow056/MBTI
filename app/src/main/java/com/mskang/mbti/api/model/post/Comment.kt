package com.mskang.mbti.api.model.post

data class Comment(
    val userId: String,
    val userName: String,
    val userMBTI: String,
    val createdAt: String,
    val content: String,
    val like: Int
)
