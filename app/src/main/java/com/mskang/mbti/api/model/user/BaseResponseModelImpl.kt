package com.mskang.mbti.api.model.user

class BaseResponseModelImpl<T: Any> {
    val code: Int? = null
    val v: String? = null
    val status: String? = null
    val detail: T? = null

    override fun toString(): String {
        return "{code:$code, v: $v, status: $status, detail: $detail}"
    }
}