package com.mskang.mbti.api

import com.mskang.mbti.api.model.mbti.update.MBTIUpdateReq
import com.mskang.mbti.api.model.post.GetPostRes
import com.mskang.mbti.api.model.post.GetPostResBody
import com.mskang.mbti.api.model.post.PostPostReq
import com.mskang.mbti.api.model.posts.PostsMBTIRes
import com.mskang.mbti.api.model.posts.PostsMBTIResBody
import com.mskang.mbti.api.model.user.BaseResponseModelImpl
import com.mskang.mbti.api.model.user.EmptyRes
import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.api.model.user.TokenRes
import com.mskang.mbti.api.model.user.TokenResBody
import com.mskang.mbti.api.model.user.signup.SignUpReq
import retrofit2.http.*

interface ServerAPI {
    @GET("/v1/user/auth")
    suspend fun getAuth(@Header("xAuth") token: String): BaseResponseModelImpl<Nothing>

    @POST("/v1/user/signin")
    suspend fun postUserSignIn(@Body signInReq: SignInReq): BaseResponseModelImpl<TokenResBody>

    @POST("/v1/user/signup")
    suspend fun postUserSignUp(@Body signUpReq: SignUpReq): BaseResponseModelImpl<Nothing>


    @POST("/v1/mbti/register")
    suspend fun postMBTIRegister(@Header("xAuth") token: String, @Body mbtiUpdateReq: MBTIUpdateReq): BaseResponseModelImpl<Nothing>

    @POST("/v1/mbti/update")
    suspend fun postMBTIUpdate(@Header("xAuth") token: String, @Body mbtiUpdateReq: MBTIUpdateReq): BaseResponseModelImpl<Nothing>


    @GET("/v1/posts/{mbti}")
    suspend fun getPosts(@Header("xAuth") token: String, @Path("mbti") mbti: String): BaseResponseModelImpl<PostsMBTIResBody>


    @POST("/v1/getpost")
    suspend fun getPost(@Header("xAuth") token: String, @Body request: Map<String, String>): BaseResponseModelImpl<GetPostResBody>


    @POST("/v1/post")
    suspend fun postPost(@Header("xAuth") token: String, @Body postPostReq: PostPostReq): BaseResponseModelImpl<Nothing>
}