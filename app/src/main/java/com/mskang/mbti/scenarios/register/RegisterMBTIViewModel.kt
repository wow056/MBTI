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
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RegisterMBTIViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
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
    val successEvent = MutableSharedFlow<Unit>()

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
        val isChanging = AtomicBoolean(false)
        launch {
            state1.collect {
                if (!isChanging.getAndSet(true)) {
                    state2.value = maxValue - state1.value
                    isChanging.set(false)
                }
            }

        }
        launch {
            state2.collect {
                if (!isChanging.getAndSet(true)) {
                    state1.value = maxValue - state2.value
                    isChanging.set(false)
                }
            }
        }
    }

    fun onClickRegister() {
        launch {
            registerRepository.updateMBTI(
                !doNotKnowValue.value,
                iValue.value,
                eValue.value,
                nValue.value,
                sValue.value,
                tValue.value,
                fValue.value,
                jValue.value,
                pValue.value
            )
            successEvent.emit(Unit)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}