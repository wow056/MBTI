package com.mskang.mbti.scenarios.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.local.app_pref.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val serverAPI: ServerAPI,
    private val appPref: AppPref,
): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + CoroutineExceptionHandler { coroutineContext, throwable ->
            launch {
                Log.e(TAG, ": ",throwable)
                toastEvent.emit("로그인에 실패하였습니다.\n" + (throwable.message ?: "알 수 없는 오류"))
            }
        }

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val toastEvent = MutableSharedFlow<String>()
    val loginSuccessEvent = MutableSharedFlow<Unit>()

    val isValid = combine(email, password) { email, password ->
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
    }

    fun setId(it: String) {
        email.value = it
    }


    fun setPassword(it: String) {
        password.value = it
    }

    fun onClickSignIn() {
        launch {
            val response = serverAPI.postUserSignIn(SignInReq(email.value, password.value))
            if (response.detail?.token != null) {
                appPref.setAccessToken(response.detail.token)
                toastEvent.emit(response.detail.token)
                loginSuccessEvent.emit(Unit)
            } else {
                toastEvent.emit("회원 정보가 없습니다.")
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}