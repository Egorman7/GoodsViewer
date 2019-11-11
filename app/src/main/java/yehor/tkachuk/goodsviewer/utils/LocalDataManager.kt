package yehor.tkachuk.goodsviewer.utils

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.SaveResult

interface LocalDataManager {
    fun save(good: Good): Single<SaveResult>
    fun getLocalList(): Single<List<Good>>
}