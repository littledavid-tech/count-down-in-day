package top.littledavid.countdowninday.presenter

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import top.littledavid.countdowninday.model.entity.TargetDay
import top.littledavid.countdowninday.model.source.TargetDayDataSource
import top.littledavid.countdowninday.presenter.contact.ConfigureWidgetContract
import top.littledavid.countdowninday.util.formatDate
import top.littledavid.countdowninday.util.subInDay
import top.littledavid.countdowninday.widget.CountDownInDayAppWidget
import java.util.*

/**
 * Created by IT on 7/21/2018.
 */
class ConfigureWidgetPresenter(
        var context: Context,
        var configureWidgetView: ConfigureWidgetContract.View,
        var targetDayDataSource: TargetDayDataSource) : ConfigureWidgetContract.Presenter {

    override fun updateWidget(title: String, remark: String, date: Date, widgetId: Int) {
        var targetDayInstance = TargetDay(widgetId, title, remark, date, false)

        //如果该TargetDay已经存在那么更新
        var flag = if (targetDayDataSource.isTargetDayExisted(widgetId)) {
            targetDayDataSource.updateTargetDay(widgetId, targetDayInstance)
        } else {//否则则添加
            targetDayDataSource.addTargetDay(targetDayInstance)
        }

        if (flag) {
            //更新或者初始化小部件
            update(title, remark, date, widgetId)

            var intent = Intent()
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            configureWidgetView.onSubmitted(intent)
        } else {
            configureWidgetView.onFailed()
        }
    }

    /**
     * 更新倒数日小部件的时候，加载信息所有
     * */
    override fun loadTargetDayInfo(widgetId: Int) {
        val dataSource = TargetDayDataSource.getInstance()
        if (dataSource.isTargetDayExisted(widgetId)) {
            val targetDay = dataSource.getTargetDay(widgetId)
            this.configureWidgetView.onLoadedInfo(targetDay)
        }
    }

    /**
     * 更新或初始化小部件
     * */
    private fun update(title: String, remark: String, date: Date, widgetId: Int) {
        val leftDay = date subInDay Calendar.getInstance().time
        val dateStr = date.formatDate()
        var remoteViews = CountDownInDayAppWidget.Factory.createInstance(context, widgetId, title, dateStr, leftDay)
        var manager = context.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager
        manager.updateAppWidget(widgetId, remoteViews)
    }
}