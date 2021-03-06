package com.mskang.mbti.scenarios.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mskang.mbti.R
import com.mskang.mbti.scenarios.board.BoardListActivity
import com.mskang.mbti.scenarios.register.RegisterActivity
import com.mskang.mbti.scenarios.ui.InputBox
import com.mskang.mbti.theme.*
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

                launch {
                    viewModel.loginSuccessEvent.collect {
                        startActivity(Intent(this@MainActivity, BoardListActivity::class.java))
                        finish()
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
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Image(painter = painterResource(id = R.drawable.ic_launcher), contentDescription = null, modifier = Modifier.size(96.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("BOUNDARY", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(56.dp))
            InputBox(
                title = "?????????",
                value = id,
                onValueChange = {
                                viewModel.setId(it  )
                },
                hint = "?????????"
            )
            Spacer(modifier = Modifier.height(16.dp))
            InputBox(
                title = "????????????",
                value = password,
                onValueChange = {
                    viewModel.setPassword(it)
                },
                hint = "?????????"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.onClickSignIn()
            },
                modifier = Modifier.fillMaxWidth(),
                shape = buttonShape,
                enabled = isLoginEnabled,
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("?????????")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(28.dp))
            Row(modifier= Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Divider(modifier = Modifier.weight(1f), color = Color(0xFFD2CFCF))
                Spacer(modifier = Modifier.width(8.dp))
                Text("or")
                Spacer(modifier = Modifier.width(8.dp))
                Divider(modifier = Modifier.weight(1f), color = Color(0xFFD2CFCF))
            }
            Spacer(modifier = Modifier.height(23.dp))
            Text("?????? ????????? ????????????????")
            Spacer(modifier = Modifier.height(13.dp))
            OutlinedButton(
                onClick = {
                    startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
                },
                colors = activeOutlinedButtonColor,
                border = activeOutlinedButtonBorder,
                shape = buttonShape,
                contentPadding = PaddingValues(vertical = 14.dp),
                modifier= Modifier.fillMaxWidth()
            ) {
                Text("????????????", color = ColorPrimary)
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


