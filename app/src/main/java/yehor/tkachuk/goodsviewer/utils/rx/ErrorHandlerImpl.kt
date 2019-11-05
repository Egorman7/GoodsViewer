package yehor.tkachuk.goodsviewer.utils.rx

import android.content.Context
import yehor.tkachuk.goodsviewer.utils.toast

class ErrorHandlerImpl(private val context: Context):
    ErrorHandler {
    override fun handleError(throwable: Throwable) {
        context.toast("Error: ${throwable.message}")
    }
}