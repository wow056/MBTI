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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mskang.mbti.scenarios.ui.InputBox
import com.mskang.mbti.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val viewModel by viewModels<RegisterViewModel>()

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
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)) {
                        Spacer(modifier = Modifier.height(24.dp))
                        InputBox(
                            title = "?????????",
                            value = viewModel.email.collectAsState().value,
                            onValueChange = {
                                viewModel.setEmail(it)
                            },
                            hint = "???????????? ??????????????????.",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        InputBox(
                            title = "????????????",
                            value = viewModel.password.collectAsState().value,
                            onValueChange = {
                                viewModel.setPassword(it)
                            },
                            hint = "??????????????? ??????????????????.",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isPassword = true
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        InputBox(
                            title = "?????????",
                            value = viewModel.nickname.collectAsState().value,
                            onValueChange = {
                                viewModel.setNickname(it)
                            },
                            hint = "?????????(2 ~ 8???)??? ??????????????????.",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        InputBox(
                            title = "????????????",
                            value = viewModel.birth.collectAsState().value,
                            onValueChange = {
                                viewModel.birth.value = it
                            },
                            hint = "???????????? 8????????? ??????????????????. (YYYYMMDD)",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("??????", style = Typography.h2, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        val sex by viewModel.sex.collectAsState()
                        Row(modifier = Modifier.fillMaxWidth()) {
                            if (sex == Sex.Male) {
                                OutlinedButton(
                                    onClick = {
                                        viewModel.sex.value = Sex.Male
                                    },
                                    colors = activeOutlinedButtonColor,
                                    border = activeOutlinedButtonBorder,
                                    shape = buttonShape,
                                    contentPadding = PaddingValues(vertical = 10.dp),
                                    modifier= Modifier.weight(1f)
                                ) {
                                    Text("??????", color = ColorPrimary)
                                }
                            } else {
                                Button(onClick = {
                                    viewModel.sex.value = Sex.Male
                                }, colors = inactiveButtonColor,
                                    shape = buttonShape,
                                    elevation = ButtonDefaults.elevation(0.dp,0.dp),
                                    contentPadding = PaddingValues(vertical = 10.dp),
                                    modifier= Modifier.weight(1f)
                                ) {

                                    Text("??????")
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (sex == Sex.Female) {
                                OutlinedButton(
                                    onClick = {
                                        viewModel.sex.value = Sex.Female
                                    },
                                    colors = activeOutlinedButtonColor,
                                    border = activeOutlinedButtonBorder,
                                    shape = buttonShape,
                                    contentPadding = PaddingValues(vertical = 10.dp),
                                    modifier= Modifier.weight(1f)
                                ) {
                                    Text("??????", color = ColorPrimary)
                                }
                            } else {
                                Button(onClick = {
                                    viewModel.sex.value = Sex.Female
                                }, colors = inactiveButtonColor,
                                    shape = buttonShape,
                                    elevation = ButtonDefaults.elevation(0.dp,0.dp),
                                    contentPadding = PaddingValues(vertical = 10.dp),
                                    modifier= Modifier.weight(1f)
                                ) {

                                    Text("??????")
                                }
                            }
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
                        Text("??????")
                    }
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.toastEvent.collect {
                        Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    viewModel.successEvent.collect {
                        startActivity(Intent(this@RegisterActivity, RegisterMBTIActivity::class.java))
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
                    text = "????????????",
                    style = navigationBarTextStyle,
                    modifier = Modifier
                        .padding(vertical = 14.dp, horizontal = 15.dp)
                )
            }
        }
    }

}
