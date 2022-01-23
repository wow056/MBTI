package com.mskang.mbti.scenarios.board.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mskang.mbti.R
import com.mskang.mbti.api.model.posts.PostsMBTIItem
import com.mskang.mbti.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : ComponentActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.uuid.value = intent.getStringExtra(keyUUID)
        setContent {
            val item by viewModel.postItem.collectAsState(null)
            var commentValue by remember {
                mutableStateOf("")
            }
            MainTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)) {
                        Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "뒤로", modifier = Modifier
                            .padding(start = 24.dp)
                            .size(16.dp)
                            .align(Alignment.CenterStart))
                        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "",
                                style = navigationBarTextStyle,
                                modifier = Modifier
                                    .padding(vertical = 14.dp, horizontal = 15.dp)
                            )
                        }
                        Image(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "메뉴",
                            Modifier
                                .padding(end = 24.dp)
                                .size(24.dp)
                                .align(
                                    Alignment.CenterEnd
                                ))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                                .padding(horizontal = 24.dp)
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = item?.contentList?.title.toString(),
                                style = previewTitleStyle,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = item?.mbti ?: "MBTI", color = ColorSecondary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = item?.nickname.toString(), color = Gray500)
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_heart),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Spacer(modifier = Modifier.width(19.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_comment),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = Color(0xFFF3F3F3))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = item?.contentList?.content.toString(),
                                style = TextStyle(
                                    color = Gray700,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                )
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                Text(
                                    text = "@",
                                    style = TextStyle(
                                        color = Gray300,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp
                                    )
                                )
                                listOf("INTP", "INFP", "ESTP").forEach {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "#$it", modifier = Modifier
                                            .background(
                                                shape = CircleShape, color = Color.White
                                            )
                                            .border(
                                                border = BorderStroke(
                                                    1.dp,
                                                    color = ColorD2CFCF
                                                ), shape = CircleShape
                                            )
                                            .padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = ColorF3F3F3)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.ic_heart), contentDescription = "좋아요")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = "16", style = TextStyle(color = Gray500, fontSize = 14.sp, lineHeight = 20.sp, ))
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.ic_comment), contentDescription = "댓글")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = "댓글", style = TextStyle(color = Gray500, fontSize = 14.sp, lineHeight = 20.sp, ))
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painter = painterResource(id = R.drawable.ic_share), contentDescription = "공유")
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = "공유", style = TextStyle(color = Gray500, fontSize = 14.sp, lineHeight = 20.sp, ))
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        repeat(5) {
                            Column(modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_base_comment),
                                        contentDescription = "댓글 프로필",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(shape = CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "MBTI", style = TextStyle(color = ColorSecondary, fontSize = 12.sp, lineHeight = 16.sp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "MBTI", style = TextStyle(color = Gray500, fontSize = 12.sp, lineHeight = 16.sp))
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Lorem ipsum dolor sit amet, consectetur adipiscin g elit. Enim curabitur etiam at eu. Est posuere sed at sit ultricies fames aliquam tincidunt.",
                                    style = TextStyle(color = Gray700, fontSize = 14.sp, lineHeight = 20.sp)
                                )
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(text = "13분 전", style = TextStyle(color = Gray300, fontSize = 12.sp, lineHeight = 16.sp))
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = "좋아요 1", style = TextStyle(color = ColorPrimary, fontSize = 12.sp, lineHeight = 16.sp))
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(text = "답글달기", style = TextStyle(color = Gray500, fontSize = 12.sp, lineHeight = 16.sp))
                                }
                            }
                        }
                    }
                    TextField(
                        value = commentValue,
                        onValueChange = { commentValue = it },
                        trailingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_send),
                                contentDescription = "보내기"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors(backgroundColor = Color.White)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    companion object {
        const val keyUUID = "keyUUIL"
    }
}