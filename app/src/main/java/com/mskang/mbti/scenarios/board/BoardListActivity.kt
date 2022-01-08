package com.mskang.mbti.scenarios.board

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mskang.mbti.R
import com.mskang.mbti.theme.*

class BoardListActivity : ComponentActivity() {

    private val viewModel by viewModels<BoardListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val boardListDataList by viewModel.boardListDataList.collectAsState(initial = emptyList())
            MainTheme {
                Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)) {
                        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "INFP 게시판",
                                style = navigationBarTextStyle,
                                modifier = Modifier
                                    .padding(vertical = 14.dp, horizontal = 15.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Image(painter = painterResource(id = R.drawable.ic_chevron_down), contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        boardListDataList.forEach { boardListData ->
                            BoardListItem(boardListData = boardListData)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                // A surface container using the 'background' color from the theme
            }
        }
    }

    @Composable
    private fun BoardListItem(boardListData: BoardListData) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(this, boardListData.id, Toast.LENGTH_SHORT)
                        .show()
                }) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = boardListData.title, style = previewTitleStyle)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = boardListData.preview, style = previewBodyStyle)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}