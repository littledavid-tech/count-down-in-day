package top.littledavid.countdowninday.presenter.contact

import android.content.Intent
import top.littledavid.countdowninday.model.entity.TargetDay
import java.util.*

/**
 * Created by IT on 7/21/2018.
 */
interface ConfigureWidgetContract {

    interface View {
        fun onSubmitted(intent: Intent)
        fun onFailed()
        fun onLoadedInfo(targetDay: TargetDay)
    }

    interface Presenter {
        fun updateWidget(title: String, remark: String, date: Date, widgetId: Int)
        fun loadTargetDayInfo(widgetId: Int)
    }
}