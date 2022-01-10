package com.mskang.mbti.scenarios.mbti

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mskang.mbti.MBTIImage
import com.mskang.mbti.scenarios.login.MainActivity
import com.mskang.mbti.scenarios.ui.InputBox
import com.mskang.mbti.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MBTIDetailActivity : ComponentActivity() {

    private val viewModel by viewModels<MBTIDetailViewModel>()

    val boldText = TextStyle(
        color = Gray900,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.selectedMBTIImage.value = intent.getSerializableExtra(
            keyMBTIImage) as? MBTIImage
        setContent {
            MainTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)) {
                    Header()

                    Column(modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {

                    val item by viewModel.selectedMBTIImage.collectAsState()

                    item?.let { nonNullItem ->
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(
                                    color = nonNullItem.color,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .aspectRatio(1f)
                                .clickable {
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painterResource(id = nonNullItem.drawableId), contentDescription = null, modifier = Modifier.fillMaxSize())

                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = nonNullItem.name, style = boldText)
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White)
                        ) {

                            var isDetailExpanded by remember { mutableStateOf(false)}
                            var isCharacter by remember { mutableStateOf(false)}
                            Row(modifier = Modifier
                                .clickable { isDetailExpanded = !isDetailExpanded }
                                .fillMaxWidth()
                                .padding(24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text="설명", style = boldText)
                                Spacer(modifier = Modifier.weight(1f))
                                Image(painter = painterResource(id = com.mskang.mbti.R.drawable.ic_chevron_down), contentDescription = null)
                            }
                            AnimatedVisibility(visible = isDetailExpanded,
                                enter = expandVertically(expandFrom = Alignment.Top),
                                exit = shrinkVertically()
                            ) {
                                Divider(color = Color(0xFFF3F3F3), modifier = Modifier.padding(horizontal = 24.dp))
                                Text(text="행동과 사고에 있어 독창적이다. 내적인 신념과 가치관은 신을 움직일 만큼 강하다. MBTI 성격중에서 가장 독립적이고 단호하며 문제에 대하여 고집이 쎄다. 자신이 가진 영감과 목적을 실현시키기려는 의지와 결단력과 인내심을 가지고 있다. 자신과 타인의 능력을 중요시하며, 목적달성을 위하여 온 시간과 노력을 바쳐 일한다. 또 행동에서 뿐만 아니라 생각에 있어서도 냉철한 혁신을 추구하는 사람들이다. 그들은 이미 확립된 권위나 널리 수용된 신념들이 있음에도 불구하고, 진실과 의미를 추구하는 데 그들의 직관적인 통찰력을 발휘한다.\n" +
                                        "\n" +
                                        "이들의 이면을 탐색해내는 명철한 분석력 때문에 일과 사람을 있는 그대로 수용하고 음미하는 것이 어렵다. 그러모로 현실을 있는 그대로 보며 구체적이고 사실적인 면을 보고자 하는 노력이 필요하다. 또한 타인의 감정을 고려하고 타인의 관점에 귀기울이는 노력이 필요하다.", modifier = Modifier.padding(16.dp))
                            }


                            Row(modifier = Modifier
                                .clickable { isCharacter = !isCharacter }
                                .fillMaxWidth()
                                .padding(24.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(text="특징", style = boldText)
                                Spacer(modifier = Modifier.weight(1f))
                                Image(painter = painterResource(id = com.mskang.mbti.R.drawable.ic_chevron_down), contentDescription = null)
                            }
                            AnimatedVisibility(visible = isCharacter,
                                enter = expandVertically(expandFrom = Alignment.Top),
                                exit = shrinkVertically()
                            ) {
                                Divider(color = Color(0xFFF3F3F3), modifier = Modifier.padding(horizontal = 24.dp))
                                Text(text="깊이 있는 대인관계를 유지하며 조용하고 신중하며 이해한 다음에 경험한다.\n" +
                                        "자기 내부에 주의를 집중함.\n" +
                                        "내부 활동과 집중력.\n" +
                                        "조용하고 신중함.\n" +
                                        "말보다는 글로 표현하는 편.\n" +
                                        "이해한 다음에 경험함.\n" +
                                        "서서히 알려지는 편.", modifier = Modifier.padding(16.dp))
                            }
                        }

                    }

                }
                    var daetgul by remember { mutableStateOf("")}
                    TextField(
                        value = daetgul,
                        onValueChange = {
                            daetgul = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text("당신의 생각은 어떤가요?", style = hintStyle)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        shape = RoundedCornerShape(4.dp),
                        trailingIcon = {
                            Image(painter = painterResource(com.mskang.mbti.R.drawable.ic_send), contentDescription = null)
                        }
                    )
                }

            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.toastEvent.collect {
                        Toast.makeText(this@MBTIDetailActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    viewModel.successEvent.collect {
                        startActivity(Intent(this@MBTIDetailActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }


    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)) {

            Image(painter = painterResource(id = com.mskang.mbti.R.drawable.ic_arrow_left), contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(28.08.dp)
                    .clickable {
                        onBackPressed()
                    }
            )

            Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "",
                    style = navigationBarTextStyle,
                    modifier = Modifier
                        .padding(vertical = 14.dp, horizontal = 15.dp)
                )
            }
        }
    }


    companion object {
        const val keyMBTIImage = "keyMBTIImage"
    }
}
