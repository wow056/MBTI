package com.mskang.mbti.scenarios.board

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mskang.mbti.theme.MainTheme
import com.mskang.mbti.theme.ColorPrimary

class BoardListActivity : ComponentActivity() {

    private val viewModel by viewModels<BoardListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val boardListDataList by viewModel.boardListDataList.collectAsState(initial = emptyList())
            MainTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier.fillMaxSize()) {

                    Box(modifier = Modifier.fillMaxWidth().background(color = ColorPrimary)) {
                        Text(
                            text = "INFP 게시판",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp, horizontal = 15.dp)
                        )
                        Text(
                            text = "글쓰기",
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp)
                        )
                    }
                    Column(modifier = Modifier.fillMaxSize().background(color = Color(0xFFF3F3F3))) {
                        boardListDataList.forEach { boardListData ->
                            BoardListItem(boardListData = boardListData)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BoardListItem(boardListData: BoardListData) {
        Surface(modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth(),
            color = Color.White
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(this, boardListData.id, Toast.LENGTH_SHORT)
                        .show()
                }) {
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = boardListData.title, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))
                    Text(text = boardListData.date, fontSize = 12.sp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(5.dp))
                }
                Text(text = boardListData.preview, fontSize = 12.sp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}