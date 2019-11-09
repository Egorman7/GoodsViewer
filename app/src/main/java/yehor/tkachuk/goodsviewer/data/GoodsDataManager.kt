package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.model.Good

interface GoodsDataManager {
    fun loadList(force: Boolean): Single<List<Good>>
    fun loadComments(productId: Int): Single<List<Comment>>
    fun postComment(product: Int, rate: Int, text: String): Single<Boolean>
}