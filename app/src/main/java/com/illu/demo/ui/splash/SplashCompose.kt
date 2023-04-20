package com.illu.demo.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

const val TIME_WAIT = 500

@Composable
fun SplashPage(onFinish: (() -> Unit) = {}) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp)
    ) {
        val (text) = createRefs()
        var visible by remember { mutableStateOf(false) }
        val fadeIn = remember { Animatable(0f) }
        val fadeOut = remember { Animatable(1f) }
        //这个地方和协程直接launch有区别，他绑定了visible这个东西，会监听visible的变化，不会提前就结束掉
        LaunchedEffect(visible) {
            if (visible) fadeIn.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = TIME_WAIT, easing = LinearEasing)
            )
            else fadeOut.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = TIME_WAIT, easing = FastOutSlowInEasing)
            )
        }
        Text(
            text = "Wan Android", color = Color.Black, fontSize = 50.sp, modifier =
            Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .alpha(
                    if (visible) {
                        fadeIn.value
                    } else {
                        fadeOut.value
                    }
                )
        )
        LaunchedEffect(Unit) {
            visible = true
            delay(TIME_WAIT.toLong())
            visible = false
            delay(TIME_WAIT.toLong())
            onFinish.invoke()
        }

    }
}