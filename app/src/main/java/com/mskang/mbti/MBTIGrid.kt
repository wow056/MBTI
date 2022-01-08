package com.mskang.mbti

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

data class MBTIImage(
    val name: String,
    @DrawableRes val drawableId: Int,
    val color: Color
)

val color1 = Color(0xFFE8D9E0)

val color2 = Color(0xFFDEEEE5)
val color3 = Color(0xFFCDE7E8)
val color4 = Color(0xFFF7E8CB)

val mbtiImageList = listOf<MBTIImage>(
    MBTIImage("INTJ", R.drawable.intj, color1),
    MBTIImage("INTJ", R.drawable.image2, color1),
    MBTIImage("INTJ", R.drawable.image3, color1),
    MBTIImage("INTJ", R.drawable.image4, color1),
    MBTIImage("INTJ", R.drawable.image5, color2),
    MBTIImage("INTJ", R.drawable.image6, color2),
    MBTIImage("INTJ", R.drawable.image7, color2),
    MBTIImage("INTJ", R.drawable.image8, color2),
    MBTIImage("INTJ", R.drawable.image9, color3),

    MBTIImage("INTJ", R.drawable.image10, color3),
    MBTIImage("INTJ", R.drawable.image11, color3),
    MBTIImage("INTJ", R.drawable.image12, color3),
    MBTIImage("INTJ", R.drawable.image13, color4),
    MBTIImage("INTJ", R.drawable.image14, color4),
    MBTIImage("INTJ", R.drawable.image15, color4),
    MBTIImage("INTJ", R.drawable.image16, color4),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MBTIImageGrid(onClickItem: (MBTIImage) -> Unit) {
    LazyVerticalGrid(cells = GridCells.Fixed(3),content = {
        items(mbtiImageList) { item ->
            Box(
                modifier = Modifier.padding(8.dp).background(color = item.color, shape = RoundedCornerShape(8.dp)).aspectRatio(1f).clickable {
                       onClickItem(item)
                },
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = item.drawableId), contentDescription = null, modifier = Modifier.fillMaxSize())
                Text(text = item.name,
                    fontWeight = FontWeight.Bold,
                    color = Gray700,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun myPreview() {
    MBTIGrid(selectedItems = listOf("INTJ", "INFP"), onSelectItem = {})
}

@Preview(showBackground = true)
@Composable
fun myPreview2() {
    MBTIImageGrid(onClickItem = {})
}
