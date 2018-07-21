package top.littledavid.countdowninday.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import top.littledavid.countdowninday.MyApplication

/**
 * Created by IT on 7/20/2018.
 */
class TargetDaySqliteOpenHelper(context: Context, version: Int) : SQLiteOpenHelper(context, "TargetDayDB", null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        //建立需要的表
        var sql = "create table TargetDay(\n" +
                "\tid integer primary key autoincrement,\n" +
                "\twidgetId integer not null,\n" +
                "title String not null, " +
                "remark String not null, " +
                "\ttargetDate String not null,\n" +
                "\tisReached String not null\n" +
                ")"
        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private val db: TargetDaySqliteOpenHelper = TargetDaySqliteOpenHelper(MyApplication.getContextInstance(), 1)

        fun getReadOnlyDB() = db.readableDatabase
        fun getWriterableDB() = db.writableDatabase
    }

}