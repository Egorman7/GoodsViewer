package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single

interface MainDataManager{
    fun test(): Single<Boolean>
}