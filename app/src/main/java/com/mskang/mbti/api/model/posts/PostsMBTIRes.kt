package com.mskang.mbti.api.model.posts

import com.mskang.mbti.api.model.user.BaseResponseModel

data class PostsMBTIRes(
    override val code: Int,
    override val v: String?,
    override val status: String,
    override val detail: List<PostsMBTIItem>?
): BaseResponseModel<List<PostsMBTIItem>>
