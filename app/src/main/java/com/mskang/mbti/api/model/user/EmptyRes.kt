package com.mskang.mbti.api.model.user

import com.mskang.mbti.api.model.user.BaseResponseModel

data class EmptyRes(
    override val code: Int,
    override val v: String?,
    override val status: String,
    override val detail: Void
): BaseResponseModel<Void>
