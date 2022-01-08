package com.mskang.mbti.api.model.post

data class GetPostResBody(
    val uuid: String,
    val title: String,
    val content: String,
    val userMBTI: String,
    val userId: String,
    val userName: String,
    val createdAt: String,
    val MBTI: List<String>,
    val comments: List<Comment>
)
