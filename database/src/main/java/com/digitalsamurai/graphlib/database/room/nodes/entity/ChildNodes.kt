package com.digitalsamurai.graphlib.database.room.nodes.entity

import com.google.gson.annotations.SerializedName

data class ChildNodes(
    @SerializedName("childs")
    var childs : ArrayList<Int>
)
