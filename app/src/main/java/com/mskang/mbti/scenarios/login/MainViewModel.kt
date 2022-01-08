package com.mskang.mbti.scenarios.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.model.user.signin.SignInReq
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
    private val serverAPI: ServerAPI
): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + CoroutineExceptionHandler { coroutineContext, throwable ->
            launch {
                Log.e(TAG, ": ",throwable)
                toastEvent.emit(throwable.message ?: "알 수 없는 오류")
            }
        }

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val toastEvent = MutableSharedFlow<String>()

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
        viewModelScope.launch {
            val result = serverAPI.postUserSignIn(SignInReq(email.value, password.value))
            toastEvent.emit(result.toString())
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}