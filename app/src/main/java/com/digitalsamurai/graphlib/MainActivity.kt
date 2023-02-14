package com.digitalsamurai.graphlib

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.view.View.OnDragListener
import android.view.View.OnLongClickListener
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.digitalsamurai.graphlib.custom.*
import com.digitalsamurai.graphlib.databinding.ActivityMainBinding
import com.digitalsamurai.tree.TreeNode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}
@Preview
@Composable
fun libraries(){
    Surface() {
        
    }
}
