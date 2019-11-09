package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.api.PostCommentRequest
import yehor.tkachuk.goodsviewer.utils.UserManager

class GoodsDataManagerImpl(private val api: MainApi, private val userManager: UserManager): GoodsDataManager{
    private var loadedList = listOf<Good>()

    override fun loadList(force: Boolean): Single<List<Good>> {
        return if(force || loadedList.isEmpty()){
            api.getGoods().doAfterSuccess {
                loadedList = it
            }
        } else {
            Single.just(loadedList)
        }
    }

    override fun loadComments(productId: Int): Single<List<Comment>> {
        return api.getComments(productId)
    }

    override fun postComment(product: Int, rate: Int, text: String): Single<Boolean> {
        return Single.create { emitter ->
            if(userManager.getToken().isBlank()){
                emitter.onSuccess(false)
            } else {
                api.postComment(userManager.getToken(), product, PostCommentRequest(rate, text)).map {
                    it.reviewId != -1
                }
            }
        }
    }
}