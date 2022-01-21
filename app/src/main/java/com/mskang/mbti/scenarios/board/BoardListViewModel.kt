package com.mskang.mbti.scenarios.board

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.local.app_pref.AppPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class BoardListViewModel @Inject constructor(
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

    val mbtiSelection = MutableStateFlow(listOf("ESTP"))

    val mbtiPath = mbtiSelection.map { list ->
        list.joinToString("&")
    }

    val postItems = appPref
        .accessTokenFlow
        .distinctUntilChanged()
        .filterNotNull()
        .combine(refreshState) { token, _ ->
            token
        }
        .flatMapLatest { token ->
            mbtiPath.mapNotNull { path ->
                serverAPI.getPosts(token, path).detail
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