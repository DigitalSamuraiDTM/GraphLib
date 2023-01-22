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
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.digitalsamurai.graphlib.custom.*
import com.digitalsamurai.graphlib.databinding.ActivityMainBinding
import com.digitalsamurai.tree.TreeNode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CreateInterface, OnLongClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var zoomer : ScaleGestureDetector

    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        navController = Navigation.findNavController(this,R.id.main_fragmentContainer)



//        val root = com.digitalsamurai.view.TreeNodeImpl.RootNode(
//            com.digitalsamurai.view.ViewNode(
//                this,
//                Point(100, 100)
//            ).also { it.text = "ROOT" })
//        l = com.digitalsamurai.view.TreeView(this, root)
//        binding.hor.addView(l.view)
//

//        val child = l.addNode(
//            com.digitalsamurai.view.ViewNode(this, Point(300, 300)).also { it.text = "CHILD1" },root)
//        val child2 = l.addNode(
//            com.digitalsamurai.view.ViewNode(this, Point(500, 500)).also { it.text = "CHILD2" },root)
//        val child3 = l.addNode(
//            com.digitalsamurai.view.ViewNode(this, Point(700, 700)).also { it.text = "CHILD3" },root)
//        val child24 = l.addNode(
//            com.digitalsamurai.view.ViewNode(this, Point(500, 900)).also { it.text = "CHILD24" },child2)
//        val child15 = l.addNode(
//            com.digitalsamurai.view.ViewNode(this, Point(-900, 300)).also { it.text = "CHILD15" },child)
//        val child156 = l.addNode(
//            com.digitalsamurai.view.ViewNode(this, Point(0, -600)).also { it.text = "CHILD156" },child15)


//        val list = l.getNodeList()
//        currentNodesList.addAll(list)
//        currentNodesList.forEach {
//            it.data.setOnLongClickListener(this)
//        }
//        Log.d("TAG", list.joinToString(", "))
//        Log.d("TAG", list.size.toString())


//        val html = Html.fromHtml("<b>BRRRRRRRRRRRRRRRR</b><br>RRRRRRRRRRRRRR<br><img src='a'/>",ImageGetter(this.applicationContext),null)

//        binding.editTextTextPersonName.setText(html)
//        binding.editTextTextPersonName.isClickable = true
//        binding.editTextTextPersonName.isFocusable = true
//        binding.editTextTextPersonName.isEnabled = true




//        binding.checkBoxMatrix.setOnClickListener {
//            l.view.isMatrixEnabled = binding.checkBoxMatrix.isChecked
//        }


//        binding.ver.setOnDragListener { view, event ->
//            return@setOnDragListener when(event?.action){
//                DragEvent.ACTION_DRAG_STARTED -> {
//                    Log.d(Constants.LOG_TAG,"STARTED X:${event.x} Y:${event.y}")
//                    true
//                }
//                DragEvent.ACTION_DRAG_ENTERED -> {
//                    Log.d(Constants.LOG_TAG,"ENTERED X:${event.x} Y:${event.y}")
//                    true
//                }
//                DragEvent.ACTION_DRAG_LOCATION -> {
//                    Log.d(Constants.LOG_TAG,"LOCATION X:${event.x} Y:${event.y}")
//                    true
//                }
//                DragEvent.ACTION_DRAG_EXITED -> {
//                    Log.d(Constants.LOG_TAG,"EXITED X:${event.x} Y:${event.y}")
//
//                    true
//                }
//                DragEvent.ACTION_DROP -> {
//                    Log.d(Constants.LOG_TAG,"DROP X:${event.x} Y:${event.y}")
//
//                    true
//                }
//                DragEvent.ACTION_DRAG_ENDED -> {
//                    Log.d(Constants.LOG_TAG,"ENDED X:${event.x} Y:${event.y}")
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//
//        }










//        binding.floatingActionButton.setOnClickListener {
//            val d = DialogAddNode(this as CreateInterface)
//            d.show(supportFragmentManager,"FAK")
//        }

    }

    override fun onLongClick(p0: View?): Boolean {


        val clip = ClipData("Fak obema?", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), ClipData.Item("Fak obema?"))
        p0?.startDragAndDrop(clip,object : View.DragShadowBuilder(p0){
            private val shadow = ColorDrawable(Color.LTGRAY)

            override fun onProvideShadowMetrics(
                size: Point,
                touch: Point
            ) {
                // Set the width of the shadow to half the width of the original View.
                val width: Int = view.width / 2

                // Set the height of the shadow to half the height of the original View.
                val height: Int = view.height / 2

                // The drag shadow is a ColorDrawable. This sets its dimensions to be the
                // same as the Canvas that the system provides. As a result, the drag shadow
                // fills the Canvas.
                shadow.setBounds(0, 0, width, height)

                // Set the size parameter's width and height values. These get back to
                // the system through the size parameter.
                size.set(width, height)

                // Set the touch point's position to

                super.onProvideShadowMetrics(size, touch)
            }

            override fun onDrawShadow(canvas: Canvas?) {
                super.onDrawShadow(canvas)
            }
        },this,0)
        Toast.makeText(this,"start", Toast.LENGTH_SHORT).show()
        return true
    }


    override fun onResume() {
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(3000)
//            binding.hor.scrollTo(lcl.xOrigin/2, lcl.yOrigin/2)
//            binding.ver.scrollTo(lcl.xOrigin/2, lcl.yOrigin/2)
//        }
        super.onResume()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        zoomer.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun created(x: Int, y: Int, text: String, parent: com.digitalsamurai.tree.TreeNode<com.digitalsamurai.view.ViewNode>) {
//        val p = l.addNode(com.digitalsamurai.view.ViewNode(this, Point(x, y)).also { it.text = text },parent)
//        currentNodesList.add(p)
//        l.view.invalidate()
    }

    companion object{
        const val LOG_TAG = "GRAPH"

//        var currentNodesList = ArrayList<com.digitalsamurai.tree.TreeNode<com.digitalsamurai.view.ViewNode>>()
    }
    class ImageGetter(private val context : Context) : Html.ImageGetter{
        override fun getDrawable(p0: String?): Drawable {
            return ResourcesCompat.getDrawable(context.resources,R.drawable.a,null)!!.also { it.setBounds(0,0,20,20) }

        }
    }
}