package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.CommentsDataManager
import yehor.tkachuk.goodsviewer.model.Comment

class CommentsViewModel(private val dataManager: CommentsDataManager) : BaseViewModel(){
    val commentList = MutableLiveData<List<Comment>>()

    fun loadComments(productId: Int){
        subscribe(dataManager.loadComments(productId)){
            commentList.value = it
        }
    }
}