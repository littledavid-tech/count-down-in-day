package top.littledavid.countdowninday

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import top.littledavid.countdowninday.model.entity.TargetDay
import top.littledavid.countdowninday.util.formatDate
import top.littledavid.countdowninday.util.subInDay
import top.littledavid.countdowninday.view.activity.ConfigureWidgetActivity

/**
 * Created by IT on 7/21/2018.
 */
class TargetDayAdapter(val context: Context, var list: List<TargetDay>) : RecyclerView.Adapter<TargetDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.count_down_in_day_widget, parent, false)
        var layoutP = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutP.bottomMargin = 10
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val targetDay = list[position]
        viewHolder!!.dateTV.text = targetDay.targetDate.formatDate()
        viewHolder.titleTV.text = targetDay.title
        viewHolder.dayTV.text = if (targetDay.isReached) {
            "Reached"
        } else {
            val day = targetDay.targetDate subInDay Calendar.getInstance().time
            day.toString()
        }
        //注册点击事件
        if (!targetDay.isReached) {
            for (item in arrayOf(viewHolder.dateTV, viewHolder.titleTV, viewHolder.dayTV, viewHolder.layout)) {
                item.setOnClickListener {
                    var intent = Intent(context, ConfigureWidgetActivity::class.java)
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, targetDay.widgetId)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView = itemView.findViewById(R.id.titleTV)
        var dateTV: TextView = itemView.findViewById(R.id.dateTV)
        var dayTV: TextView = itemView.findViewById(R.id.dayTV)
        var layout: RelativeLayout = itemView.findViewById(R.id.countDownInDatWidgetLayoutR)
    }
}