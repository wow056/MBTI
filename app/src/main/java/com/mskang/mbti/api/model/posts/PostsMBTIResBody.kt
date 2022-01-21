package com.mskang.mbti.api.model.posts

import kotlin.math.min

data class PostsMBTIResBody(
    val mainContentData: List<PostsMBTIItem>,
    val commentListData: List<Int>,
    val likeListData: List<Int>,
    val nicknameList: List<String>,
    val mbtiList: List<String?>
) {
    val minLength: Int
        get() = minOf(mainContentData.size, commentListData.size, likeListData.size, nicknameList.size, mbtiList.size)
}
