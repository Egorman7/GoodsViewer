package yehor.tkachuk.goodsviewer.utils.rx

interface ErrorHandler {
    fun handleError(throwable: Throwable)
}