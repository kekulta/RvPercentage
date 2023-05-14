package ru.kekulta.rvpercentage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import ru.kekulta.rvpercentage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding wasn't iet initialized or already cleared!" }
    private val percentage = 10
    private val listener = PercentageScrollListener(percentage) { item, visibility ->
        Log.d(LOG_TAG, "Item #$item is now $visibility% visible")
        Toast.makeText(this, "Item #$item is now $percentage% visible", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.apply {
            layoutManager =
                GridLayoutManager(this@MainActivity, 2)
            adapter = Adapter().apply {
                list = buildList { repeat(45) { add(Item(it)) } }
            }
            addOnScrollListener(listener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val LOG_TAG = "MainActivity"
    }
}