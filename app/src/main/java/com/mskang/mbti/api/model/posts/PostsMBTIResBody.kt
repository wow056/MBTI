package com.mskang.mbti.api.model.posts

data class PostsMBTIResBody(
    val mainContentData: List<PostsMBTIItem>,
    val commentList: List<String>,
    val likeList: List<Int>
)
