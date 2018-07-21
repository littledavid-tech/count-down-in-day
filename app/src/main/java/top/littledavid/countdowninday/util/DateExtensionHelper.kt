package top.littledavid.countdowninday.util

import java.util.*

/**
 * Created by IT on 7/21/2018.
 */
//格式化日期
fun Date.formatDate() = DateUtil.mDateFormater.format(this)

//计算两个日期之间的差值
infix fun Date.subInDay(date: Date): Int {
    var temp = this.time - date.time
    return Math.floor(temp * 1.0 / (1000 * 60 * 60 * 24)).toInt()
}

fun String.toDate(): Date = DateUtil.mDateFormater.parse(this)