package com.mskang.mbti.api

import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.api.model.user.TokenRes
import com.mskang.mbti.api.model.user.signup.SignUpReq
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerAPI {
    @POST("/v1/user/signin")
    suspend fun postUserSignIn(@Body signInReq: SignInReq): TokenRes

    @POST("/v1/user/signup")
    suspend fun postUserSignUp(@Body signUpReq: SignUpReq): TokenRes
}