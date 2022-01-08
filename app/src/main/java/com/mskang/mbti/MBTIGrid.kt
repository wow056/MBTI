package com.mskang.mbti

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mskang.mbti.theme.ColorPrimary
import com.mskang.mbti.theme.Gray100
import com.mskang.mbti.theme.Gray700


val mbtiList = listOf(
"ISTJ",
"ISFJ",	"INFJ",	"INTJ" ,
"ISTP",	"ISFP",	"INFP"	,"INTP" ,
"ESTP",	"ESFP",	"ENFP"	,"ENTP" ,
"ESTJ",	"ESFJ",	"ENFJ"	,"ENTJ",
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MBTIGrid(selectedItems: List<String>, onSelectItem: (String) -> Unit) {
    LazyVerticalGrid(cells = GridCells.Fixed(4),content = {
        items(mbtiList) { item ->
            val backgroundColor = if (selectedItems.contains(item)) {
                ColorPrimary
            } else {
                Gray100
            }

            val textColor = if (selectedItems.contains(item)) {
                Color.White
            } else {
                Gray700
            }

            Text(text = item,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .background(color = backgroundColor, shape = CircleShape)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable { onSelectItem(item) },
                color = textColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun myPreview() {
    MBTIGrid(selectedItems = listOf("INTJ", "INFP"), onSelectItem = {})
}