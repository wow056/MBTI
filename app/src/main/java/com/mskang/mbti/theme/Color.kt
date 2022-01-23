package com.mskang.mbti.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val ColorPrimary = Color(0xFFEF4C4C)
val ColorSecondary = Color(0xFF4C83EF)

val Gray50 = Color(0xFFF8F8F8)
val Gray100 = Color(0xFFF3F3F3)
val Gray300 = Color(0xFFD2CfCF)
val Gray500 = Color(0xFF7A7676)
val Gray700 = Color(0xFF3D3A3A)
val Gray800 = Color(0xFF333333)
val Gray900 = Color(0xFF201F1F)
val ColorD2CFCF = Color(0xFFD2CFCF)
val ColorF3F3F3 = Color(0xFFF3F3F3)

val activeOutlinedButtonColor
    @Composable
    get() = ButtonDefaults.outlinedButtonColors(
        backgroundColor = Color.White,
        contentColor = ColorPrimary,
        disabledContentColor = ColorPrimary
    )

val activeOutlinedButtonSecondaryColor
    @Composable
    get() = ButtonDefaults.outlinedButtonColors(
        backgroundColor = Color.White,
        contentColor = ColorPrimary,
        disabledContentColor = ColorSecondary
    )

val activeOutlinedButtonBorder
    @Composable
    get() = BorderStroke(1.dp, color = ColorPrimary)


val activeOutlinedButtonSecondaryBorder
    @Composable
    get() = BorderStroke(1.dp, color = ColorSecondary)


val inactiveButtonColor
    @Composable
    get() = ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = Gray300
    )

