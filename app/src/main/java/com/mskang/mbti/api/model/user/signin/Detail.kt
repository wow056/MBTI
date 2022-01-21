package com.mskang.mbti.api.model.user.signin

import com.mskang.mbti.api.model.post.Content

data class Detail(
    val commentlike: List<Any>,
    val commentlist: List<Any>,
    val contentList: Content,
    val likeCount: LikeCount,
    val mbti: String,
    val nickname: String,
    val voteone: Int,
    val votetwo: Int
)