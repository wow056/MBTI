package com.mskang.mbti.scenarios.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mskang.mbti.theme.Typography
import com.mskang.mbti.theme.hintStyle

@Composable
fun InputBox(title: String, value: String, onValueChange: (String) -> Unit, hint: String, keyboardOptions: KeyboardOptions = KeyboardOptions.Default) {
    Text(title, style = Typography.h2, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(9.dp))
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(hint, style = hintStyle)
        },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(4.dp)
    )
}