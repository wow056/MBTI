package com.mskang.mbti.scenarios.board

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.api.ServerAPI
import com.mskang.mbti.api.model.post.PostPostReq
import com.mskang.mbti.api.model.posts.PostsMBTIItem
import com.mskang.mbti.local.app_pref.AppPref
import com.mskang.mbti.scenarios.login.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val boardRepository: BoardRepository
): ViewModel(), CoroutineScope {

    val toastEvent = MutableSharedFlow<String>()
    val successEvent = MutableSharedFlow<Unit>()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + CoroutineExceptionHandler { coroutineContext, throwable ->
            launch {
                Log.e(TAG, ": ",throwable)
                toastEvent.emit("로드에 실패하였습니다.\n" + (throwable.message ?: "알 수 없는 오류"))
            }
        }

    val title = MutableStateFlow("")
    val tagList = MutableStateFlow(listOf<String>("ESTP"))
    val body = MutableStateFlow("")

    fun onClickFooter() {
        launch {
            boardRepository.postPost(title.value, body.value, tagList.value)
            successEvent.emit(Unit)
        }
    }

    companion object {
        private const val TAG = "BoardListViewModel"
    }
}