package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.Good

class GoodsDataManagerImpl(private val api: MainApi): GoodsDataManager{
    private var loadedList = listOf<Good>()

    override fun loadList(force: Boolean): Single<List<Good>> {
        return if(force){
            api.getGoods().doAfterSuccess {
                loadedList = it
            }
        } else {
            Single.just(loadedList)
        }
    }
}