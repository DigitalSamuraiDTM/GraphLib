package com.digitalsamurai.graphlib.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digitalsamurai.graphlib.R
import kotlinx.coroutines.delay

@Composable
fun SnackbarMessage(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    painter: Painter? = null,
    timeMillis: Long = Long.MAX_VALUE
) {

    var visibilityState by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = Unit) {
            delay(timeMillis)
            visibilityState = false
    }

    AnimatedVisibility(
        visible = visibilityState,
        enter = slideInVertically(tween(250)) { it },
        exit = slideOutVertically(tween(250)) { it },
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier
                .padding(10.dp)
                .shadow(
                    10.dp,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White)
                .padding(start = 5.dp, top = 5.dp, bottom = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = "Image",
                    modifier = Modifier.padding(end = 5.dp, start = 5.dp)
                )
            }
            Text(text = text, textAlign = textAlign, fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
private fun PreviewSnackbar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SnackbarMessage(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            "Example of text. More text. More text. More text. " +
                    "More text. More text. More text. More text. More text. More text." +
                    " More text. More text. More text. More text. More text. More text. More text. More text. ",
            painter = painterResource(id = R.drawable.ic_info_outline_black), timeMillis = 1000L
        )
    }
}