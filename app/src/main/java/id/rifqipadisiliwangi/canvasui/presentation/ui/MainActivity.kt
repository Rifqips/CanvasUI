package id.rifqipadisiliwangi.canvasui.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isGone
import id.rifqipadisiliwangi.canvasui.R
import id.rifqipadisiliwangi.canvasui.data.database.DatabaseHelper
import id.rifqipadisiliwangi.canvasui.data.model.SideMultipliedData
import id.rifqipadisiliwangi.canvasui.data.utils.CustomDialog
import id.rifqipadisiliwangi.canvasui.data.utils.Value
import id.rifqipadisiliwangi.canvasui.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), CustomDialog.DialogListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showCustomDialog()
        dbHelper = DatabaseHelper(this)
        getRange()
        deleteValue()

        binding.btnInput.setOnClickListener {
            showCustomDialog()
        }

    }

    private fun getRange(){
        binding.btnRange.setOnClickListener {
            val squareSize = 10.0
            val minX = Random.nextDouble(0.0, 300.0)
            val minY = Random.nextDouble(0.0, 300.0)

            for (i in 0 until 10) {
                val x = minX + Random.nextDouble(squareSize)
                val y = minY + Random.nextDouble(squareSize)
                dbHelper.insertPoint(x, y)
            }

            binding.myCanvas.invalidate()

            binding.btnInput.isGone = true
        }
    }

    private fun deleteValue(){
        binding.btnDelete.setOnClickListener {
            val pointIdToDelete = 1
            val deletedRows = dbHelper.deleteAllPoints()
            if (deletedRows > 0) {
                Toast.makeText(this, "Deleted point$pointIdToDelete", Toast.LENGTH_SHORT).show()
                binding.myCanvas.invalidate()
            } else {
                Toast.makeText(this, "Point $pointIdToDelete not found", Toast.LENGTH_SHORT).show()
            }
            binding.btnInput.isGone = false
        }
    }
    private fun showCustomDialog() {
        val customDialog = CustomDialog(this, this)
        customDialog.show()
        binding.btnDelete.isGone = true
    }

    override fun onDialogPositiveClick(text: String) {
        Value.valueReceipts = text.toDouble()
        Toast.makeText(this, "Input Text: $text", Toast.LENGTH_SHORT).show()
        binding.btnDelete.isGone = false
    }
}