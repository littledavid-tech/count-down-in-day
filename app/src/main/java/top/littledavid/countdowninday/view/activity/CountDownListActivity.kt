package top.littledavid.countdowninday.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_count_down_list.*
import top.littledavid.countdowninday.R
import top.littledavid.countdowninday.TargetDayAdapter
import top.littledavid.countdowninday.model.entity.TargetDay
import top.littledavid.countdowninday.model.source.TargetDayDataSource
import top.littledavid.countdowninday.presenter.TargetDayListPresenter
import top.littledavid.countdowninday.presenter.contact.TargetDayListContract

class CountDownListActivity : AppCompatActivity(), TargetDayListContract.View {

    private val presenter: TargetDayListContract.Presenter = TargetDayListPresenter(this, this, TargetDayDataSource.getInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down_list)

        targetDayRV.layoutManager = LinearLayoutManager(this)

        reachedRB.setOnCheckedChangeListener { _, isChecked ->
            presenter.loadTargetDayList(isChecked)
        }

        presenter.loadTargetDayList(false)
    }

    /**
     * 刷新按钮的点击事件
     * */
    fun refresh(view: View) {
        presenter.loadTargetDayList(reachedRB.isChecked)
    }

    /**
     * 显示加载出来的数据
     * */
    override fun onLoadedTargetList(list: List<TargetDay>, isUnreached: Boolean) {
        targetDayRV.adapter = TargetDayAdapter(this, list)
    }
}
