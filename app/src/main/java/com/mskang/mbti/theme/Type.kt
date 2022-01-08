package com.mskang.mbti.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mskang.mbti.R

val NotoSansFamily = FontFamily(
    Font(R.font.noto_sans_regular),
    Font(R.font.noto_sans_black, FontWeight.Black),
    Font(R.font.noto_sans_bold, FontWeight.Bold),
    Font(R.font.noto_sans_light, FontWeight.Light),
    Font(R.font.noto_sans_medium, FontWeight.Medium),
    Font(R.font.noto_sans_thin, FontWeight.Thin)
)

val hintStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Gray300,
    fontSize = 16.sp,
    lineHeight = 20.sp
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = NotoSansFamily,
    h1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        color = Gray800
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        color = Gray900
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        color = Gray300,
        fontSize = 16.sp,
        lineHeight = 20.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)