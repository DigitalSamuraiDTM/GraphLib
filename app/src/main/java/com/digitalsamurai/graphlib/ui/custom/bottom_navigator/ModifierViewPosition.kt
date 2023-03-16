package com.digitalsamurai.graphlib.ui.custom.bottom_navigator

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density

fun Modifier.position(x : Int, y : Int
) = this.then(
    ViewPosition(x,y)
)


class ViewPosition(val x : Int, val y : Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any {
        return this@ViewPosition
    }
}