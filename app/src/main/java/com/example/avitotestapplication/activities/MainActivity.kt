package com.example.avitotestapplication.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avitotestapplication.R
import com.example.avitotestapplication.adapters.Adapter
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private companion object Extras {
        const val RecyclerAdapterDataExtra = "com.example.avitotestapplication.RECYCLER_ADAPTER_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialization()
    }

    private fun initialization() {
        val recyclerViewAdapter = Adapter()

        this.recyclerView = findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            adapter = recyclerViewAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 1)
        } ?: throw RuntimeException("RecyclerView doesn't exist")

        timer(period = 5000) {
            runOnUiThread(recyclerViewAdapter::addItem)
        }
    }

    override fun onStop() {
        super.onStop()

        intent.putExtras(Bundle().apply {
            putSerializable(RecyclerAdapterDataExtra, recyclerView.adapter as Adapter)
        })
    }

    override fun onResume() {
        super.onResume()

        this.recyclerView.adapter = intent?.extras?.getSerializable(RecyclerAdapterDataExtra)
                as? Adapter ?: this.recyclerView.adapter
        (this.recyclerView.layoutManager as GridLayoutManager).spanCount =
            when(resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 4
                Configuration.ORIENTATION_PORTRAIT -> 2
                else -> 0
            }
    }
}