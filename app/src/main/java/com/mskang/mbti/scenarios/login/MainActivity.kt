package com.mskang.mbti.scenarios.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mskang.mbti.theme.ColorPrimary
import com.mskang.mbti.theme.MainTheme
import com.mskang.mbti.theme.Typography
import com.mskang.mbti.theme.hintStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Body(viewModel)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.toastEvent.collect {
                        Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @Composable
    fun Body(viewModel: MainViewModel = viewModel()) {
        val id by viewModel.email.collectAsState()
        val password by viewModel.password.collectAsState()
        val isLoginEnabled by viewModel.isValid.collectAsState(initial = false)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text("아이콘 들어갈 위치")
            Text("아이디", style = Typography.h2, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(9.dp))
            OutlinedTextField(
                value = id,
                onValueChange = {
                    viewModel.setId(it)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
                placeholder = {
                    Text("아이디", style = hintStyle)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("비밀번호", style = Typography.h2, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(9.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    viewModel.setPassword(it)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
                placeholder = {
                    Text("비밀번호", style = hintStyle)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                 viewModel.onClickSignIn()
            },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                enabled = isLoginEnabled
            ) {
                Text("로그인")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier= Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier.weight(1f))
                Text("or")
                Divider(modifier = Modifier.weight(1f))
            }
            Text("아직 회원이 아니신가요?")
            OutlinedButton(onClick = { /*TODO*/ },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.White,
                    contentColor = ColorPrimary
                ),
                modifier= Modifier.fillMaxWidth()
            ) {
                Text("회원가입")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MainTheme {
            Body()
        }
    }
}


