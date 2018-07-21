package top.littledavid.countdowninday.presenter.contact

import top.littledavid.countdowninday.model.entity.TargetDay

/**
 * Created by IT on 7/21/2018.
 */
interface TargetDayListContract {
    interface View {
        fun onLoadedTargetList(list: List<TargetDay>, isUnreached: Boolean)
    }

    interface Presenter {
        fun loadTargetDayList(isReached: Boolean)
    }
}