package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Good

interface GoodsDataManager {
    fun loadList(force: Boolean): Single<List<Good>>
}