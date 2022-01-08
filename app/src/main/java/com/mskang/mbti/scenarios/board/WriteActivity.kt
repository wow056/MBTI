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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mskang.mbti.MBTIGrid
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
            val navController = rememberNavController()
            MainTheme {

                NavHost(navController = navController, startDestination = "write") {
                    composable("write") {
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
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("select_mbti")
                                    },verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.ic_hashtag), contentDescription = null)
                                    Spacer(modifier = Modifier.width(13.dp))
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
                                        viewModel.tagList.collectAsState().value.forEach { item ->
                                            Text("#$item", modifier= Modifier.background(
                                                shape = CircleShape, color = Color.White
                                            ).border(border = BorderStroke(1.dp, color = Color(0xFFD2CFCF)), shape = CircleShape).padding(horizontal = 12.dp, vertical = 4.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
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
                    }
                    composable("select_mbti") {
                        var selectedList by remember{ mutableStateOf(viewModel.tagList.value) }
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.background),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)) {
                                Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = null, modifier = Modifier.padding(vertical = 20.dp, horizontal = 28.dp).clickable {
                                    navController.popBackStack()
                                })

                            }
                            Spacer(modifier = Modifier.height(1.dp))
                            Column(
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.height(24.dp))
                                Text("연관된 MBTI를 선택해주세요.", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("투표기능을 사용시 선택된 MBTI만 투표가 가능합니다.")
                                Spacer(modifier = Modifier.height(24.dp))
                                MBTIGrid(selectedItems = selectedList, onSelectItem = { selectedItem ->
                                    selectedList = if (selectedList.contains(selectedItem)) {
                                        selectedList.filterNot { selectedItem == it }
                                    } else {
                                        selectedList + selectedItem
                                    }
                                })
                                Spacer(modifier = Modifier.weight(1f))
                                Button(onClick = {
                                    viewModel.tagList.value = selectedList
                                    navController.popBackStack()
                                },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = buttonShape,
                                    contentPadding = PaddingValues(vertical = 14.dp)
                                ) {
                                    Text("필터 적용")
                                }
                            }
                        }
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