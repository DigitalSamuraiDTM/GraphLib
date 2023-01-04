package com.digitalsamurai.graphlib.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton

@SuppressLint("ViewConstructor")
class ViewNode(context: Context,
               var viewPosition: Point
) : AppCompatButton(context) {
    init {
        this.layoutParams = FrameLayout.LayoutParams(200,200)
    }
}