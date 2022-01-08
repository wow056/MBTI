package com.mskang.mbti.api.model.user

data class TokenRes(
    val token: String?,
    override val code: Int,
    override val v: String?,
    override val status: String,
    override val detail: TokenResBody?
): BaseResponseModel<TokenResBody>
