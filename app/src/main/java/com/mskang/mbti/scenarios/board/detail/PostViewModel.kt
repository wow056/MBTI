package com.mskang.mbti.scenarios.board.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.local.app_pref.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PostViewModel @Inject constructor(
    private val appPref: AppPref,
    private val serverAPI: ServerAPI
): ViewModel(), CoroutineScope {

    val toastEvent = MutableSharedFlow<String>()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + CoroutineExceptionHandler { coroutineContext, throwable ->
            launch {
                Log.e(TAG, ": ",throwable)
                toastEvent.emit("로드에 실패하였습니다.\n" + (throwable.message ?: "알 수 없는 오류"))
            }
        }

    val refreshState = MutableStateFlow(0)

    val uuid = MutableStateFlow<String?>(null)

    val postItem = appPref
        .accessTokenFlow
        .distinctUntilChanged()
        .filterNotNull()
        .combine(refreshState) { token, _ ->
            token
        }
        .flatMapLatest { token ->
            uuid.filterNotNull().mapNotNull { uuid ->
                serverAPI.getPost(token, mapOf("uuid" to uuid)).detail
            }
        }
        .catch { throwable ->
            toastEvent.emit(throwable.message ?: "알 수 없는 오류")
        }

    fun refresh() {
        refreshState.value = refreshState.value + 1
    }


    companion object {
        private const val TAG = "BoardListViewModel"
    }
}