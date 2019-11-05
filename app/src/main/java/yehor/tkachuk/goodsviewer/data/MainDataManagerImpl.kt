package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single

class MainDataManagerImpl : MainDataManager{
    var c = 0
    override fun test(): Single<Boolean> {
        return Single.create { emitter ->
            if(c++ % 2 == 0){
                emitter.onSuccess(true)
            } else {
                emitter.onError(Throwable("No chetnoe!"))
            }
        }
    }
}