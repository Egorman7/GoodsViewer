package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Comment

interface CommentsDataManager {
    fun loadComments(productId: Int): Single<List<Comment>>
}