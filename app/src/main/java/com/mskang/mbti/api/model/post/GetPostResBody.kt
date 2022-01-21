package com.mskang.mbti.api.model.post

data class GetPostResBody(
    val commentlike: List<Int>,
    val commentlist: List<Comment>,
    val contentList: Content,
    val likeCount: LikeCount,
    val mbti: String?,
    val nickname: String,
    val voteone: Int,
    val votetwo: Int,
    val commentList: List<String>,
    val commentLike: List<Int>,

)
