package com.mskang.mbti.api.model.user.signup

import com.google.gson.annotations.SerializedName

data class SignUpReq(
    val email: String,
    val password: String,
    val nickname: String,
    @SerializedName("MBTI")
    val mbti: String,
)
