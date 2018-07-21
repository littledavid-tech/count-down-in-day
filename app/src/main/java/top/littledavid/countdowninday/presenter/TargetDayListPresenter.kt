package top.littledavid.countdowninday.presenter

import android.content.Context
import android.webkit.DateSorter
import top.littledavid.countdowninday.model.source.TargetDayDataSource
import top.littledavid.countdowninday.presenter.contact.TargetDayListContract

/**
 * Created by IT on 7/21/2018.
 */
class TargetDayListPresenter(val context: Context, val view: TargetDayListContract.View, val dataSource: TargetDayDataSource) : TargetDayListContract.Presenter {

    override fun loadTargetDayList(isReached: Boolean) {
        var list = if (isReached) this.dataSource.getReachedTargetDayList() else dataSource.getUnreachedTargetDayList()
        this.view.onLoadedTargetList(list, isReached)
    }
}