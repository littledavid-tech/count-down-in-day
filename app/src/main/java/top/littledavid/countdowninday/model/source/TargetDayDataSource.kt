package top.littledavid.countdowninday.model.source

import top.littledavid.countdowninday.model.entity.TargetDay
import top.littledavid.countdowninday.model.provider.DBTargetDayDataSourceProvider

/**
 * Created by IT on 7/20/2018.
 */
interface TargetDayDataSource {

    /**
     * @return 未到达的TargetDay的列表
     * */
    fun getUnreachedTargetDayList(): List<TargetDay>

    /**
     * @return 已经到达的TargetDay的列表
     * */
    fun getReachedTargetDayList(): List<TargetDay>

    fun getTargetDay(widgetId: Int): TargetDay

    /**
     * 更新现有的TargetDay
     *
     * @param widgetId 窗口小部件的ID
     * @param targetDay 新的数据
     * */
    fun updateTargetDay(widgetId: Int, targetDay: TargetDay): Boolean

    /**
     *添加一个新的TargetDay
     *
     * @param targetDay 需要添加的TargetDay
     * */
    fun addTargetDay(targetDay: TargetDay): Boolean

    /**
     * 删除一个TargetDay
     *
     * @param widgetId 要被删除的TargetDay的对应的小部件的id
     * */
    fun deleteTargetDay(widgetId: Int): Boolean

    /**
     * 判断该Target是否存在
     * */
    fun isTargetDayExisted(widgetId: Int): Boolean

    companion object Factory {
        private val mTargetDayDataSource = DBTargetDayDataSourceProvider()
        fun getInstance(flag: String = "DB"): TargetDayDataSource {
            when (flag) {

            }
            return mTargetDayDataSource
        }
    }
}