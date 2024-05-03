package com.mevi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mevi.ui.R


val Poppins = FontFamily(
    fonts = listOf(
        Font(resId = R.font.poppins_regular, weight = FontWeight.Normal),
        Font(resId = R.font.poppins_medium, weight = FontWeight.Medium),
    )
)
val Inter = FontFamily(
    fonts = listOf(
        Font(resId = R.font.inter_regular, weight = FontWeight.Normal),
        Font(resId = R.font.inter_light, weight = FontWeight.Light),
        Font(resId = R.font.inter_medium, weight = FontWeight.Medium),
        Font(resId = R.font.inter_semibold, weight = FontWeight.SemiBold),
    )
)

val Typography = Typography(

    displayLarge = TextStyle(
        //Headline1
        fontSize = 92.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 112.sp,
        letterSpacing = (-1.5).sp
    ),
    displayMedium = TextStyle(
        //Headline2
        fontSize = 58.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = TextStyle(
        //Headline3
        fontSize = 48.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
    ),
    headlineMedium = TextStyle(
        //Headline4
        fontSize = 32.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp,
        letterSpacing = (0.25).sp
    ),
    headlineSmall = TextStyle(
        //Headline5
        fontSize = 24.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 30.sp,
    ),
    titleLarge = TextStyle(
        //Headline6
        fontSize = 20.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0.15).sp
    ),
    titleMedium = TextStyle(
        //subtitle1
        fontSize = 16.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = (0.15).sp

    ),
    titleSmall = TextStyle(
        //subtitle2
        fontSize = 14.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
        letterSpacing = (0.1).sp

    ),
    bodyLarge = TextStyle(
        //body1
        fontSize = 16.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = (0.5).sp
    ),
    bodyMedium = TextStyle(
        //body2
        fontSize = 14.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
        letterSpacing = (0.25).sp
    ),
    bodySmall = TextStyle(
        //caption
        fontSize = 12.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = (0.4).sp
    ),
    labelLarge = TextStyle(
        //button
        fontSize = 16.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
    ),
    labelSmall = TextStyle(
        //overline
        fontSize = 10.sp,
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        letterSpacing = (1.5).sp
    ),
)