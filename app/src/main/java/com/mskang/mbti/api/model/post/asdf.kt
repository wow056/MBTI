package com.mskang.mbti.api.model.post

data class asdf(
    val commentlike: List<Int>,
    val commentlist: List<Comment>,
    val contentList: ContentList,
    val likeCount: LikeCount,
    val mbti: String?,
    val nickname: String,
    val voteone: Int,
    val votetwo: Int
)