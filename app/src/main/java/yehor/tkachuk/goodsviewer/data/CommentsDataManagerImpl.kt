package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.Comment

class CommentsDataManagerImpl(private val api: MainApi) : CommentsDataManager{
    override fun loadComments(productId: Int): Single<List<Comment>> {
        return api.getComments(productId)
    }
}