package com.digitalsamurai.graphlib.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.digitalsamurai.graphlib.database.room.nodes.position.NodePosition


@Composable
fun BottomNavigator(rootNode : NodePresentation){
    Row(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
        .height(50.dp)
        .padding(10.dp, 0.dp, 10.dp, 0.dp)) {

    }
}

@Composable
@Preview
private fun previewBottomNavigator(){
    val node1 = NodePresentation(LibNode("","title",0, ChildNodes(ArrayList()),0))
    BottomNavigator(node1)
}