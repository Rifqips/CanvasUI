package id.rifqipadisiliwangi.canvasui.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import id.rifqipadisiliwangi.canvasui.data.database.DatabaseHelper

class MyCanvas(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private val paintStroke = Paint()

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paintStroke.color = Color.YELLOW
        paintStroke.style = Paint.Style.STROKE
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val squareSize = Value.valueReceipts

        val minX = 30.0
        val minY = 60.0
        val maxX = minX + squareSize
        val maxY = minY + squareSize

        val left = (minX / 1) - (squareSize / 30)
        val top = (minY / 1) - (squareSize / 30)
        val right = left + squareSize
        val bottom = top + squareSize

        val dbHelper = DatabaseHelper(context)
        val pointsInSquare = dbHelper.getPointsInRange(minX, minY, maxX, maxY)
        for (point in pointsInSquare) {
            canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), 5f, paint)
            canvas.drawRect(left.toFloat(), top.toFloat(),right.toFloat(), bottom.toFloat(), paintStroke)
        }
    }
}
