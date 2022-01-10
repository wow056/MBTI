package com.mskang.mbti.scenarios.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.local.app_pref.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    private val auth = Firebase.auth

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

    init {
        launch {
            auth.currentUser?.reload()
            val user = auth.currentUser
            if (user != null) {
                loginSuccessEvent.emit(Unit)
            }
        }
    }

    fun onClickSignIn() {
        launch {
            auth.signInWithEmailAndPassword(email.value, password.value).await()

            if (auth.currentUser != null) {
                loginSuccessEvent.emit(Unit)
            } else {
                toastEvent.emit("로그인에 실패하였습니다.")
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}