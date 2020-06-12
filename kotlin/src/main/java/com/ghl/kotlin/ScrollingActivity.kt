package com.ghl.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    val first: Int = 1//立即赋值
    val second = 2
    val text = "who am I"
    var c: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        fab.setOnClickListener { v ->
            c = 100
            var a = 1
            val s1 = "a is ${a}"
            a = 2
            val s2 = "${s1.replace("is", "was")},but now is ${a}"

            print(s2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 有返回值的
     */
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    /**
     * void
     *

     */
    fun show(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "sum of 10 and 20 is ${sum(10, 20)}", Toast.LENGTH_SHORT).show()
    }
}
