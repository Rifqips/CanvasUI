package id.rifqipadisiliwangi.canvasui.data.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import id.rifqipadisiliwangi.canvasui.R

class CustomDialog(context: Context, private val listener: DialogListener) : Dialog(context) {

    private lateinit var editText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog_layout)

        editText = findViewById(R.id.et_side)

        val btnOk = findViewById<Button>(R.id.btn_input)
        btnOk.setOnClickListener {
            var text = editText.text.toString()
            listener.onDialogPositiveClick(text)
            dismiss()
        }

    }

    interface DialogListener {
        fun onDialogPositiveClick(text: String)
    }
}
