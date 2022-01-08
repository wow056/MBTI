package com.mskang.mbti.scenarios.board.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
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
import androidx.compose.ui.unit.dp
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
            MainTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)) {
                        Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "",
                                style = navigationBarTextStyle,
                                modifier = Modifier
                                    .padding(vertical = 14.dp, horizontal = 15.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        item?.let {postsMBTIItem ->
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(text = postsMBTIItem.title, style = previewTitleStyle)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "MBTI", color = ColorSecondary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = postsMBTIItem.userName.toString(), color = Gray500)
                                Spacer(modifier = Modifier.weight(1f))
                                Image(painter = painterResource(id = R.drawable.ic_heart), contentDescription = null)
                                Spacer(modifier = Modifier.width(10.dp))
                                Spacer(modifier = Modifier.width(19.dp))
                                Image(painter = painterResource(id = R.drawable.ic_comment), contentDescription = null)
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Text(text = postsMBTIItem.content, style = previewBodyStyle)
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
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