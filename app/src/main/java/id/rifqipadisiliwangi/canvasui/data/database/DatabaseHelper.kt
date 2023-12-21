package id.rifqipadisiliwangi.canvasui.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.rifqipadisiliwangi.canvasui.data.model.Point

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "canvas_database.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "points"
        private const val COLUMN_ID = "id"
        private const val COLUMN_X = "x"
        private const val COLUMN_Y = "y"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_X REAL, $COLUMN_Y REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertPoint(x: Double, y: Double): Long {
        val values = ContentValues()
        values.put(COLUMN_X, x)
        values.put(COLUMN_Y, y)

        val db = this.writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getPointsInRange(minX: Double, minY: Double, maxX: Double, maxY: Double): List<Point> {
        val pointList = mutableListOf<Point>()
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_X >= $minX AND $COLUMN_X <= $maxX " +
                "AND $COLUMN_Y >= $minY AND $COLUMN_Y <= $maxY"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val x = cursor.getDouble(cursor.getColumnIndex(COLUMN_X))
                val y = cursor.getDouble(cursor.getColumnIndex(COLUMN_Y))
                val point = Point(id, x, y)
                pointList.add(point)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return pointList
    }
    fun deleteAllPoints(): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, null, null)
        db.close()
        return result
    }
}


