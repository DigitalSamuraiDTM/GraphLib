package com.digitalsamurai.graphlib.ui.libs.item

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.database.room.libs.Lib
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LibraryItem(lib : Lib, modifier: Modifier = Modifier){
    val typography = MaterialTheme.typography
    Row(modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .shadow(2.dp, RectangleShape,
            ambientColor = Color.White,
            spotColor = Color.Black)
        .padding(5.dp)
        ,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = lib.libName,
             style = typography.h4,
             modifier = Modifier.padding(4.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = lib.dateCreating.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            style = typography.body2, modifier = Modifier.padding(5.dp))
    }
}

@Composable
@Preview
fun previewItem(){
    LibraryItem(lib = Lib("test", LocalDateTime.now()))
}