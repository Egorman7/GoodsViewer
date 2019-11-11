package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.api.CommentResponse

interface GoodsDataManager {
    fun loadList(force: Boolean): Single<List<Good>>
    fun loadComments(productId: Int): Single<List<Comment>>
    fun postComment(productId: Int, text: String, rate: Int): Single<CommentResponse>
}