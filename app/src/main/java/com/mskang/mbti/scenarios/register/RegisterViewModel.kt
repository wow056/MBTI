package com.mskang.mbti.scenarios.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.api.model.user.signup.SignUpReq
import com.mskang.mbti.local.app_pref.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val serverAPI: ServerAPI
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
    val password2 = MutableStateFlow("")
    val birth = MutableStateFlow("")
    val nickname = MutableStateFlow("")
    val sex = MutableStateFlow<Sex?>(null)
    val toastEvent = MutableSharedFlow<String>()
    val successEvent = MutableSharedFlow<Unit>()

    val isValid = combine(email, password, password2, nickname) { email, password, password2, nickname ->
        email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.isNotEmpty()
                && password2.isNotEmpty()
                && nickname.isNotEmpty()
    }

    fun setEmail(it: String) {
        email.value = it
    }


    fun setPassword(it: String) {
        password.value = it
    }


    fun setPassword2(it: String) {
        password2.value = it
    }

    fun setNickname(it: String) {
        nickname.value = it
    }

    fun onClickRegister() {
        launch {
            try {
                serverAPI.postUserSignUp(
                    SignUpReq(
                        email = email.value,
                        password = password.value,
                        password2 = password.value,
                        nickname = nickname.value,
                    )
                )
                successEvent.emit(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "onClickRegister: ", e)
                toastEvent.emit("회원가입 실패, ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}