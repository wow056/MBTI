package com.mskang.mbti.scenarios.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mskang.mbti.scenarios.login.MainActivity
import com.mskang.mbti.scenarios.ui.InputBox
import com.mskang.mbti.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterMBTIActivity : ComponentActivity() {

    private val viewModel by viewModels<RegisterMBTIViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)) {
                    Header()
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)) {


                        OutlinedButton(
                            onClick = {
                                Toast.makeText(this@RegisterMBTIActivity, "님 MBTI는 님이 찾아보셈 ㄹㅇㅋㅋ", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            colors = activeOutlinedButtonColor,
                            border = activeOutlinedButtonBorder,
                            shape = buttonShape,
                            contentPadding = PaddingValues(vertical = 14.dp),
                            modifier= Modifier.fillMaxWidth()
                        ) {
                            Text("MBTI 결과 연동하기", color = ColorPrimary)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Checkbox(checked = viewModel.doNotKnowValue.collectAsState().value, onCheckedChange = {
                                viewModel.doNotKnowValue.value = it
                            })
                            Text("MBTI 상세값 모름")
                        }

                        Spacer(modifier = Modifier.height(26.dp))

                        if (viewModel.doNotKnowValue.collectAsState().value) {
                            MBTISelectorList()
                        } else {
                            MBTISliderList()
                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                          viewModel.onClickRegister()
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        shape = buttonShape,
                        contentPadding = PaddingValues(vertical = 14.dp)
                    ) {
                        Text("다음")
                    }
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.toastEvent.collect {
                        Toast.makeText(this@RegisterMBTIActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    viewModel.successEvent.collect {
                        startActivity(Intent(this@RegisterMBTIActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }


    @Composable
    private fun MBTISelectorList() {
        MBTISelectorRow(
            leftText = "I",
            leftValue = viewModel.iValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.iValue.value = it
            },
            rightText = "E",
            rightValue = viewModel.eValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.eValue.value = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MBTISelectorRow(
            leftText = "N",
            leftValue = viewModel.nValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.nValue.value = it
            },
            rightText = "S",
            rightValue = viewModel.sValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.sValue.value = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MBTISelectorRow(
            leftText = "T",
            leftValue = viewModel.tValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.tValue.value = it
            },
            rightText = "F",
            rightValue = viewModel.fValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.fValue.value = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MBTISelectorRow(
            leftText = "J",
            leftValue = viewModel.jValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.jValue.value = it
            },
            rightText = "P",
            rightValue = viewModel.pValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.pValue.value = it
            }
        )
    }

    @Composable
    private fun MBTISliderList() {
        MBTISliderRow(
            leftTitle = "I",
            leftDescription = "내향성",
            leftValue = viewModel.iValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.iValue.value = it
            },
            rightTitle = "E",
            rightDescription = "외향성",
            rightValue = viewModel.eValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.eValue.value = it
            }
        )

        MBTISliderRow(
            leftTitle = "N",
            leftDescription = "내향성",
            leftValue = viewModel.nValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.nValue.value = it
            },
            rightTitle = "S",
            rightDescription = "외향성",
            rightValue = viewModel.sValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.sValue.value = it
            }
        )

        MBTISliderRow(
            leftTitle = "T",
            leftDescription = "내향성",
            leftValue = viewModel.tValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.tValue.value = it
            },
            rightTitle = "F",
            rightDescription = "외향성",
            rightValue = viewModel.fValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.fValue.value = it
            }
        )

        MBTISliderRow(
            leftTitle = "J",
            leftDescription = "내향성",
            leftValue = viewModel.jValue.collectAsState().value,
            onChangeLeftValue = {
                viewModel.jValue.value = it
            },
            rightTitle = "P",
            rightDescription = "외향성",
            rightValue = viewModel.pValue.collectAsState().value,
            onChangeRightValue = {
                viewModel.pValue.value = it
            }
        )
    }

    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)) {

            Image(painter = painterResource(id = com.mskang.mbti.R.drawable.ic_chevron_down), contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(28.08.dp)
                    .clickable {
                        onBackPressed()
                    }
            )

            Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "회원가입",
                    style = navigationBarTextStyle,
                    modifier = Modifier
                        .padding(vertical = 14.dp, horizontal = 15.dp)
                )
            }
        }
    }

    @Stable
    @Composable
    fun MBTISliderRow(
        leftTitle: String,
        leftDescription: String,
        leftValue: Int,
        onChangeLeftValue: (Int) -> Unit,
        rightTitle: String,
        rightDescription: String,
        rightValue: Int,
        onChangeRightValue: (Int) -> Unit
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = leftTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = leftDescription, fontSize = 12.sp,color = Gray500)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = rightDescription, fontSize = 12.sp,color = Gray500)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = rightTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.fillMaxWidth()) {

            TextField(
                value = leftValue.toString(),
                onValueChange = {
                    onChangeLeftValue(it.toIntOrNull() ?: 0)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.width(60.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(4.dp)
            )
            val leftColor = if (leftValue > rightValue) {
                ColorPrimary
            } else {
                Gray300
            }
            val rightColor = if (rightValue > leftValue) {
                ColorSecondary
            } else {
                Gray300
            }
            val dotColor = if (leftValue > rightValue) {
                leftColor
            } else {
                rightColor
            }
            Slider(
                value = leftValue.toFloat(),
                valueRange = 0f .. (leftValue + rightValue).toFloat(),
                onValueChange = {
                    onChangeLeftValue(it.toInt())
                },
                colors = SliderDefaults.colors(
                    thumbColor = dotColor,
                    activeTrackColor = leftColor,
                    inactiveTrackColor = rightColor,
                ),
                modifier = Modifier.weight(1f)
            )

            TextField(
                value = rightValue.toString(),
                onValueChange = {
                    onChangeRightValue(it.toIntOrNull() ?: 0)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.width(60.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(4.dp)
            )
        }
    }


    @Stable
    @Composable
    fun MBTISelectorRow(
        leftText: String,
        leftValue: Int,
        onChangeLeftValue: (Int) -> Unit,
        rightText: String,
        rightValue: Int,
        onChangeRightValue: (Int) -> Unit
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            if (leftValue > rightValue) {
                OutlinedButton(
                    onClick = {
                        onChangeLeftValue(100)
                    },
                    colors = activeOutlinedButtonColor,
                    border = activeOutlinedButtonBorder,
                    shape = buttonShape,
                    contentPadding = PaddingValues(vertical = 10.dp),
                    modifier= Modifier.weight(1f)
                ) {
                    Text(leftText, color = ColorPrimary)
                }
            } else {
                Button(onClick = {
                    onChangeLeftValue(100)
                }, colors = inactiveButtonColor,
                    shape = buttonShape,
                    elevation = ButtonDefaults.elevation(0.dp,0.dp),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    modifier= Modifier.weight(1f)
                ) {

                    Text(leftText)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (rightValue > leftValue) {
                OutlinedButton(
                    onClick = {
                        onChangeRightValue(100)
                    },
                    colors = activeOutlinedButtonSecondaryColor,
                    border = activeOutlinedButtonSecondaryBorder,
                    shape = buttonShape,
                    contentPadding = PaddingValues(vertical = 10.dp),
                    modifier= Modifier.weight(1f)
                ) {
                    Text(rightText, color = ColorSecondary)
                }
            } else {
                Button(onClick = {
                    onChangeRightValue(100)
                }, colors = inactiveButtonColor,
                    shape = buttonShape,
                    elevation = ButtonDefaults.elevation(0.dp,0.dp),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    modifier= Modifier.weight(1f)
                ) {

                    Text(rightText)
                }
            }
        }
    }

}
