package yehor.tkachuk.goodsviewer.utils

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.Toast

private var toast: Toast? = null
fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT){
    toast?.cancel()
    toast = Toast.makeText(this, text, length)
    toast?.show()
}

fun View.collapse(duration: Long){
    val targetWith = (measuredWidth * 0.3).toInt()
    val animator = ValueAnimator.ofInt(measuredWidth, targetWith)
    animator.addUpdateListener {
        val v = it.animatedValue as Int
        val params = layoutParams
        params.width = v
        layoutParams = params
    }
    animator.setDuration(duration)
    animator.start()
}