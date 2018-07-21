package top.littledavid.countdowninday.model.entity

import java.util.*

/**
 * Created by IT on 7/20/2018.
 */
data class TargetDay(var widgetId: Int, var title: String, var remark: String, var targetDate: Date, var isReached: Boolean)