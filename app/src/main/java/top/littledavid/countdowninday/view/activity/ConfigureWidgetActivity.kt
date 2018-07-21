package top.littledavid.countdowninday.view.activity

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.icu.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_config_widget.*
import top.littledavid.countdowninday.R
import top.littledavid.countdowninday.model.entity.TargetDay
import top.littledavid.countdowninday.model.provider.DBTargetDayDataSourceProvider
import top.littledavid.countdowninday.model.source.TargetDayDataSource
import top.littledavid.countdowninday.presenter.ConfigureWidgetPresenter
import top.littledavid.countdowninday.presenter.contact.ConfigureWidgetContract
import javax.sql.DataSource

/**
 * 因为我们使用了和Activity来配置Widget，所以onUpdate并不会在创建Widget时调用，
 * 所以我们需要自己来完成初始化的操作
 * */
class ConfigureWidgetActivity : AppCompatActivity(), ConfigureWidgetContract.View {


    private var mWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var presenter: ConfigureWidgetContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_widget)

        //获取WidgetId
        this.mWidgetId = intent.extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        //实例化presenter
        presenter = ConfigureWidgetPresenter(this, this, TargetDayDataSource.getInstance())

        presenter.loadTargetDayInfo(mWidgetId)
    }

    /**
     * 提交数据
     * */
    fun submit(view: View) {

        if (titleET.text.isEmpty() || remarkET.text.isEmpty()) {
            Toast.makeText(this, "Title or remark cannot be null", Toast.LENGTH_SHORT).show()
            return
        }

        val title = titleET.text.toString()
        val remark = remarkET.text.toString()
        var calendar = android.icu.util.Calendar.getInstance()
        calendar.set(targetDateDP.year, targetDateDP.month, targetDateDP.dayOfMonth)
        presenter.updateWidget(title, remark, calendar.time, this.mWidgetId)
    }

    /**
     * Activity返回值
     * */
    override fun onSubmitted(intent: Intent) {
        this.setResult(android.app.Activity.RESULT_OK, intent)
        finish()
    }

    /**
     * 初始化/更新 小部件失败
     * */
    override fun onFailed() {
        Toast.makeText(this, "Failed to submit", Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示数据
     * */
    override fun onLoadedInfo(targetDay: TargetDay) {
        titleET.setText(targetDay.title)
        remarkET.setText(targetDay.remark)
        var calendar = Calendar.getInstance()
        calendar.time = targetDay.targetDate
        targetDateDP.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
}
