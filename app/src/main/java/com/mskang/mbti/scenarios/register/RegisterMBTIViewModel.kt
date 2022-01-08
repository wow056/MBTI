package com.mskang.mbti.scenarios.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.model.mbti.update.MBTIUpdateReq
import com.mskang.mbti.api.model.user.signin.SignInReq
import com.mskang.mbti.api.model.user.signup.SignUpReq
import com.mskang.mbti.local.app_pref.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RegisterMBTIViewModel @Inject constructor(
    private val serverAPI: ServerAPI,
    private val appPref: AppPref,
): ViewModel(), CoroutineScope {

    val defaultValue = 50
    val maxValue = 100

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + CoroutineExceptionHandler { coroutineContext, throwable ->
            launch {
                Log.e(TAG, ": ",throwable)
                toastEvent.emit("로그인에 실패하였습니다.\n" + (throwable.message ?: "알 수 없는 오류"))
            }
        }

    val toastEvent = MutableSharedFlow<String>()

    val doNotKnowValue = MutableStateFlow(false)

    val iValue = MutableStateFlow(defaultValue)
    val eValue = MutableStateFlow(defaultValue)

    val nValue = MutableStateFlow(defaultValue)
    val sValue = MutableStateFlow(defaultValue)

    val tValue = MutableStateFlow(defaultValue)
    val fValue = MutableStateFlow(defaultValue)

    val jValue = MutableStateFlow(defaultValue)
    val pValue = MutableStateFlow(defaultValue)

    init {
        bindMaxValue(iValue, eValue)
        bindMaxValue(nValue, sValue)
        bindMaxValue(tValue, fValue)
        bindMaxValue(jValue, pValue)

    }

    private fun bindMaxValue(state1: MutableStateFlow<Int>, state2: MutableStateFlow<Int>) {
        launch {
            state1.collect {
                state2.value = maxValue - state1.value
            }

        }
        launch {
            state2.collect {
                state1.value = maxValue - state2.value
            }
        }
    }

    fun onClickRegister() {
        launch {
            serverAPI.postMBTIRegister(
                appPref.accessTokenFlow.filterNotNull().first(),
                MBTIUpdateReq(
                    eValue.value.toString(),
                    iValue.value.toString(),
                    nValue.value.toString(),
                    sValue.value.toString(),
                    tValue.value.toString(),
                    fValue.value.toString(),
                    jValue.value.toString(),
                    pValue.value.toString()
                )
            )
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}