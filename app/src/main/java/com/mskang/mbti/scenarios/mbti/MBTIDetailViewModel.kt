package com.mskang.mbti.scenarios.mbti

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.MBTIImage
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
class MBTIDetailViewModel @Inject constructor(
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

    val toastEvent = MutableSharedFlow<String>()
    val successEvent = MutableSharedFlow<Unit>()

    val selectedMBTIImage = MutableStateFlow<MBTIImage?>(null)

    init {

    }


    companion object {
        private const val TAG = "MainViewModel"
    }
}