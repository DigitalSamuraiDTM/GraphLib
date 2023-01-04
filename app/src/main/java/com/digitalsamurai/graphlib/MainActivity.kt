package com.digitalsamurai.graphlib

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.AutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import com.digitalsamurai.graphlib.custom.*
import com.digitalsamurai.graphlib.databinding.ActivityMainBinding
import com.digitalsamurai.graphlib.math.tree.TreeNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CreateInterface {
    private lateinit var binding : ActivityMainBinding
    private lateinit var zoomer : ScaleGestureDetector
    private lateinit var l : TreeView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        val root = TreeNodeImpl.RootNode(ViewNode(this, Point(100,100)).also { it.text = "ROOT" })
        l = TreeView(this,root)
        binding.hor.addView(l.view)


        val child = l.addNode(ViewNode(this,Point(300,300)).also { it.text = "CHILD1" },root)
        val child2 = l.addNode(ViewNode(this,Point(500,500)).also { it.text = "CHILD2" },root)
        val child3 = l.addNode(ViewNode(this,Point(700,700)).also { it.text = "CHILD3" },root)
        val child24 = l.addNode(ViewNode(this,Point(500,900)).also { it.text = "CHILD24" },child2)
        val child15 = l.addNode(ViewNode(this,Point(-900,300)).also { it.text = "CHILD15" },child)
        val child156 = l.addNode(ViewNode(this,Point(0,-600)).also { it.text = "CHILD156" },child15)


        val list = l.getNodeList()
        currentNodesList.addAll(list)
        Log.d("TAG", list.joinToString(", "))
        Log.d("TAG", list.size.toString())


//        val html = Html.fromHtml("<b>BRRRRRRRRRRRRRRRR</b><br>RRRRRRRRRRRRRR<br><img src='a'/>",ImageGetter(this.applicationContext),null)

//        binding.editTextTextPersonName.setText(html)
//        binding.editTextTextPersonName.isClickable = true
//        binding.editTextTextPersonName.isFocusable = true
//        binding.editTextTextPersonName.isEnabled = true




        binding.checkBoxMatrix.setOnClickListener {
            l.view.isMatrixEnabled = binding.checkBoxMatrix.isChecked
        }





        binding.floatingActionButton.setOnClickListener {
            val d = DialogAddNode(this as CreateInterface)
            d.show(supportFragmentManager,"FAK")
        }

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
        zoomer.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun created(x: Int, y: Int, text: String, parent: TreeNode<ViewNode>) {
        val p = l.addNode(ViewNode(this,Point(x,y)).also { it.text = text },parent)
        currentNodesList.add(p)
//        l.view.invalidate()
    }

    companion object{
        var currentNodesList = ArrayList<TreeNode<ViewNode>>()
    }
    class ImageGetter(private val context : Context) : Html.ImageGetter{
        override fun getDrawable(p0: String?): Drawable {
            return ResourcesCompat.getDrawable(context.resources,R.drawable.a,null)!!.also { it.setBounds(0,0,20,20) }

        }
    }
}