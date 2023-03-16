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
import com.digitalsamurai.graphlib.ui.custom.bottom_navigator.position
import com.digitalsamurai.graphlib.ui.custom.tree_layout.node.ItemTreeNode
import com.digitalsamurai.graphlib.ui.main.vm.NodeViewModel


@Composable
fun NodeView(data : ItemTreeNode.TreeNodeData, viewModel: NodeViewModel) {
    Button(
        onClick = { viewModel.clickEvent(data.nodeIndex) },
        modifier = Modifier
            .width(data.preferences.width.dp)
            .height(data.preferences.height.dp)
            .position(data.preferences.x, data.preferences.y),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
    ) {
        Text(
            text = data.title,
            textAlign = TextAlign.Center,
            color = Color.White
        )

    }

}


@Composable
@Preview
fun previewNodePresentation() {

    val mockViewModel = object : NodeViewModel {
        override fun clickEvent(node: Long) {

        }
    }
    val nodeData  =ItemTreeNode.TreeNodeData("title",0,ItemTreeNode.TreeNodePreferences(0,0,200,200))
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NodeView(nodeData,mockViewModel)
    }
}