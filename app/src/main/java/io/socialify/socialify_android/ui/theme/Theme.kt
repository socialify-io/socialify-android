package io.socialify.socialify_android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val text: Color,
    val clickableText: Color,
    val surface: Color,
    val onSurface: Color,

    val gray: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        text = Color.Unspecified,
        clickableText = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,

        gray = Color.Unspecified
    )
}

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor,
    secondary = PrimaryColor,
    background = BackgroundDark
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor,
    secondary = PrimaryColor,
    background = BackgroundLight

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SocialifyandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val extendedColors = if (darkTheme) {
        ExtendedColors(
            text = TextDark,
            clickableText = ClickableTextDark,
            surface = SurfaceDark,
            onSurface = OnSurfaceDark,

            gray = Color.DarkGray
        )
    } else {
        ExtendedColors(
            text = Color.Black,
            clickableText = PrimaryColor,
            surface = SurfaceLight,
            onSurface = OnSurfaceLight,

            gray = Color.LightGray
        )
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object SocialifyandroidTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}

