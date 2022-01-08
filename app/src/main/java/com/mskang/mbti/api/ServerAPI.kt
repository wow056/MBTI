package com.mskang.mbti.api

import com.mskang.mbti.api.model.mbti.update.MBTIUpdateReq
import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.api.model.user.TokenRes
import com.mskang.mbti.api.model.user.signup.SignUpReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ServerAPI {
    @POST("/v1/user/signin")
    suspend fun postUserSignIn(@Body signInReq: SignInReq): TokenRes

    @POST("/v1/user/signup")
    suspend fun postUserSignUp(@Body signUpReq: SignUpReq)


    @POST("/v1/mbti/register")
    suspend fun postMBTIRegister(@Header("token") token: String, @Body mbtiUpdateReq: MBTIUpdateReq)

    @POST("/v1/mbti/update")
    suspend fun postMBTIUpdate(@Header("token") token: String, @Body mbtiUpdateReq: MBTIUpdateReq)
}