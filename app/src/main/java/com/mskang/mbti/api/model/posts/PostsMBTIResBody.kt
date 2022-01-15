package com.mskang.mbti.api.model.posts

import kotlin.math.min

data class PostsMBTIResBody(
    val mainContentData: List<PostsMBTIItem>,
    val commentList: List<Int>,
    val likeList: List<Int>
) {
    fun getTriple(): List<Triple<PostsMBTIItem, Int, Int>> {
        val length = minOf(mainContentData.size, commentList.size, likeList.size)
        return List(length) { index ->
            Triple(mainContentData[index], commentList[index], likeList[index])
        }
    }
}
