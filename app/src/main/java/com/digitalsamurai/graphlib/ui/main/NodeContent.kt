package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalsamurai.graphlib.database.room.nodes.NodePresentation
import com.digitalsamurai.graphlib.database.room.nodes.node.LibNode
import com.digitalsamurai.graphlib.database.room.nodes.node.entity.ChildNodes
import com.digitalsamurai.graphlib.ui.main.vm.NodePresentationViewModel


@Composable
fun NodePresentation.view(viewModel: NodePresentationViewModel){
    Button(
        onClick = { viewModel.clickEvent(this) },
        modifier = Modifier
            .width(this.nodeViewProperty.width.dp)
            .height(this.nodeViewProperty.height.dp)
            .position(this.nodePosition.xPosition,this.nodePosition.yPosition),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)) {
            Text(text = this@view.nodeInfo.title,
                textAlign = TextAlign.Center,
                color = Color.White)

    }
}


@Composable
@Preview
fun previewNodePresentation(){

    val mockViewModel = object : NodePresentationViewModel{
        override fun clickEvent(nodePresentation: NodePresentation) {

        }
    }
    val testNode = LibNode("test","It is a title",0, ChildNodes(),0)
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        NodePresentation(testNode).view(mockViewModel)
    }
}