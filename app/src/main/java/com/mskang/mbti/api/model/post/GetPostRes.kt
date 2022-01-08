package com.mskang.mbti.api.model.post

import com.mskang.mbti.api.model.user.BaseResponseModel

data class GetPostRes(
    override val code: Int,
    override val v: String?,
    override val status: String,
    override val detail: GetPostResBody?
): BaseResponseModel<GetPostResBody>
