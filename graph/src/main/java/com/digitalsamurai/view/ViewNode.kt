package com.digitalsamurai.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton

@SuppressLint("ViewConstructor")
class ViewNode(context: Context,
               var viewPosition: Point
) : AppCompatButton(context) {
    init {
        this.layoutParams = FrameLayout.LayoutParams(200,200)
    }




}