package com.mskang.mbti.api.model.user

interface BaseResponseModel<T> {
    val code: Int
    val v: String?
    val status: String
    val detail: T?
}