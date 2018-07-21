package top.littledavid.countdowninday.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import android.widget.RemoteViews
import top.littledavid.countdowninday.R
import top.littledavid.countdowninday.model.source.TargetDayDataSource
import top.littledavid.countdowninday.util.formatDate
import top.littledavid.countdowninday.util.subInDay
import top.littledavid.countdowninday.view.activity.ConfigureWidgetActivity

class CountDownInDayAppWidget : AppWidgetProvider() {

    /**
     * 更新小部件
     * */
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        var source = TargetDayDataSource.getInstance()
        appWidgetIds!!.forEach {
            if (!source.isTargetDayExisted(it))
                return
            Log.e("TAG", "mWidgetId:" + it)

            var targetDay = source.getTargetDay(it)
            val date = targetDay.targetDate
            val leftDay = date subInDay Calendar.getInstance().time
            val dateStr = date.formatDate()

            var remoteViews = CountDownInDayAppWidget.Factory.createInstance(context!!, it, targetDay.title, dateStr, leftDay)
            var manager = context.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager
            manager.updateAppWidget(it, remoteViews)
        }
    }

    /**
     * 删除小部件
     * */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        var source = TargetDayDataSource.Factory.getInstance()
        appWidgetIds!!.forEach {
            source.deleteTargetDay(it)
        }
    }

    companion object Factory {
        var i = 0
        fun createInstance(context: Context, widgetId: Int, title: String, date: String, leftDay: Int): RemoteViews {
            var remoteViews = RemoteViews(context.packageName, R.layout.count_down_in_day_widget)
            //设置事件-开启ConfigureWidgetActivity
            var intent = Intent(context, ConfigureWidgetActivity::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            /**
             * 这里对PendingIntent的处理不是很恰当
             * 因为PendingIntent自身的特性的原因，这里使用了一个自增的数来避免一些出乎意料的bug
             * 实在是因为才疏学浅，没有更好的解决办法，还望大拿提供好的办法
             * */
            var pendingIntent = PendingIntent.getActivity(context, i++, intent, 0)

            arrayListOf(R.id.dayFlagTV, R.id.dayTV, R.id.titleTV, R.id.dateTV, R.id.countDownInDatWidgetLayoutR).forEach {
                remoteViews.setOnClickPendingIntent(it, pendingIntent)
            }

            //设置显示信息
            remoteViews.setTextViewText(R.id.dateTV, date)
            remoteViews.setTextViewText(R.id.dayTV, leftDay.toString())
            remoteViews.setTextViewText(R.id.titleTV, title)

            return remoteViews
        }
    }
}
