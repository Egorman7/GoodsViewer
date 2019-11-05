package yehor.tkachuk.goodsviewer.utils

import android.content.Context
import android.widget.Toast

private var toast: Toast? = null
fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT){
    toast?.cancel()
    toast = Toast.makeText(this, text, length)
    toast?.show()
}