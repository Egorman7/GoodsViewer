package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.utils.SingleLiveEvent

class CommentsViewModel(private val dataManager: GoodsDataManager) : BaseViewModel(){
    val commentList = MutableLiveData<List<Comment>>()
    val commentPosted = SingleLiveEvent<Unit>()

    fun loadComments(productId: Int){
        subscribe(dataManager.loadComments(productId)){
            commentList.value = it
        }
    }

    fun postComment(productId: Int, text: String, rating: Int){
        subscribe(dataManager.postComment(productId, text, rating)){
            commentPosted.call()
            loadComments(productId)
        }
    }
}