package com.mskang.mbti.scenarios.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.mskang.mbti.R
import com.mskang.mbti.api.model.posts.PostsMBTIItem
import com.mskang.mbti.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class WriteActivity : ComponentActivity() {

    private val viewModel by viewModels<WriteViewModel>()

    val dividerColor = Color(0xFFF3F3F3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)) {
                        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "글쓰기",
                                style = navigationBarTextStyle,
                                modifier = Modifier
                                    .padding(vertical = 14.dp, horizontal = 15.dp)
                            )
                        }

                        Text(text = "게시", color = ColorPrimary, modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 24.dp)
                            .clickable {
                                viewModel.onClickFooter()
                            })
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxSize()
                    ) {
                        TextField(
                            value = viewModel.title.collectAsState().value,
                            onValueChange = {
                                viewModel.title.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            placeholder = {
                                Text("글 제목", style = hintStyle)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                        )
                        Divider(color = dividerColor, modifier = Modifier.padding(horizontal = 24.dp))

                        TextField(
                            value = viewModel.body.collectAsState().value,
                            onValueChange = {
                                viewModel.body.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            placeholder = {
                                Text("내용을 입력하세요.", style = hintStyle)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                    Divider(color = dividerColor)
                    Row {
                        Image(painter = painterResource(id = R.drawable.ic_rectangle), contentDescription = null)
                        Text("사진추가")
                        Image(painter = painterResource(id = R.drawable.ic_rectangle), contentDescription = null)
                        Text("투표")
                    }
                }
                // A surface container using the 'background' color from the theme
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.toastEvent.collect {
                        Toast.makeText(this@WriteActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    viewModel.successEvent.collect {
                        finish()
                    }
                }
            }
        }
    }
}