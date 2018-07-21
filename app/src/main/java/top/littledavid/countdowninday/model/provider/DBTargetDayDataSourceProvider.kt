package top.littledavid.countdowninday.model.provider

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ContentValues
import android.database.Cursor
import top.littledavid.countdowninday.MyApplication
import top.littledavid.countdowninday.model.db.TargetDaySqliteOpenHelper
import top.littledavid.countdowninday.model.entity.TargetDay
import top.littledavid.countdowninday.model.source.TargetDayDataSource
import top.littledavid.countdowninday.util.formatDate
import top.littledavid.countdowninday.util.toDate
import java.util.*

/**
 * Created by IT on 7/20/2018.
 */
class DBTargetDayDataSourceProvider : TargetDayDataSource {

    override fun getTargetDay(widgetId: Int): TargetDay {
        var db = TargetDaySqliteOpenHelper(MyApplication.getContextInstance(), 1).readableDatabase
        var cursor = TargetDaySqliteOpenHelper.getReadOnlyDB().query("TargetDay", null, "widgetId=?", arrayOf(widgetId.toString()), null, null, null)
//        var cursor = db.query("TargetDay", null, "widgetId=?", arrayOf(widgetId.toString()), null, null, null)
        cursor.moveToFirst()
        var obj = decodeCursor(cursor)
        cursor.close()
        return obj
    }

    override fun getUnreachedTargetDayList(): List<TargetDay> {
        var targetDayList = mutableListOf<TargetDay>()
        var cursor = TargetDaySqliteOpenHelper.getReadOnlyDB().query("TargetDay", null, "isReached='No'", null, null, null, "targetDate")
        while (cursor.moveToNext()) {
            targetDayList.add(decodeCursor(cursor))
        }
        cursor.close()
        return targetDayList
    }

    override fun getReachedTargetDayList(): List<TargetDay> {
        var targetDayList = mutableListOf<TargetDay>()
        var cursor = TargetDaySqliteOpenHelper.getReadOnlyDB().query("TargetDay", null, "isReached='Yes'", null, null, null, "targetDate")
        while (cursor.moveToNext()) {
            targetDayList.add(decodeCursor(cursor))
        }
        cursor.close()
        return targetDayList
    }

    override fun updateTargetDay(widgetId: Int, targetDay: TargetDay): Boolean {
        var db = TargetDaySqliteOpenHelper.getWriterableDB()
        var contentValues = ContentValues().apply {
            put("widgetId", targetDay.widgetId)
            put("title", targetDay.title)
            put("remark", targetDay.remark)
            put("targetDate", targetDay.targetDate.formatDate())
            put("isReached", if (targetDay.isReached) "Yes" else "No")
        }

        var id = db.update("TargetDay", contentValues, "widgetId=?", arrayOf(widgetId.toString()))
        return id > 0
    }

    override fun addTargetDay(targetDay: TargetDay): Boolean {
        var db = TargetDaySqliteOpenHelper.getWriterableDB()
        var contentValues = ContentValues().apply {
            put("widgetId", targetDay.widgetId)
            put("title", targetDay.title)
            put("remark", targetDay.remark)
            put("targetDate", targetDay.targetDate.formatDate())
            put("isReached", if (targetDay.isReached) "Yes" else "No")
        }
        var id = db.insert("TargetDay", null, contentValues)
        return id > 0
    }

    override fun deleteTargetDay(widgetId: Int): Boolean {
        var db = TargetDaySqliteOpenHelper.getWriterableDB()
        var id = db.delete("TargetDay", "widgetId=?", arrayOf(widgetId.toString()))
        return id > 0
    }

    override fun isTargetDayExisted(widgetId: Int): Boolean {
        var cursor = TargetDaySqliteOpenHelper.getReadOnlyDB().query("TargetDay", null, "widgetId=?", arrayOf(widgetId.toString()), null, null, null)
        return cursor.count > 0
    }


    private fun decodeCursor(cursor: Cursor): TargetDay {
        val widgetId = cursor.getInt(cursor.getColumnIndex("widgetId"))
        val title = cursor.getString(cursor.getColumnIndex("title"))
        val remark = cursor.getString(cursor.getColumnIndex("remark"))
        val date = cursor.getString(cursor.getColumnIndex("targetDate")).toDate()
        val isReached = cursor.getString(cursor.getColumnIndex("isReached"))

        return TargetDay(widgetId, title, remark, date, isReached == "Yes")
    }
}