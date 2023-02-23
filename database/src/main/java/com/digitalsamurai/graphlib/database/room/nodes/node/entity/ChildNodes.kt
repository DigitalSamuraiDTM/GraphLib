package com.digitalsamurai.graphlib.database.room.nodes.node.entity

import com.google.gson.annotations.SerializedName

data class ChildNodes(
    @SerializedName("childs")
    var childs : ArrayList<Int>
)
