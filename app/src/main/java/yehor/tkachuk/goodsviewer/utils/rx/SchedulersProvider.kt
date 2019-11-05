package yehor.tkachuk.goodsviewer.utils.rx

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun io(): Scheduler
    fun main(): Scheduler
}