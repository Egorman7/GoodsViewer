package yehor.tkachuk.goodsviewer.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog

private var toast: Toast? = null
fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT){
    toast?.cancel()
    toast = Toast.makeText(this, text, length)
    toast?.show()
}

fun View.collapse(duration: Long, onFinish: (() -> Unit)? = null){
    val targetWith = (measuredWidth * 0.2).toInt()
    val animator = ValueAnimator.ofInt(measuredWidth, targetWith)
    animator.addUpdateListener {
        val v = it.animatedValue as Int
        val params = layoutParams
        params.width = v
        layoutParams = params
    }
    animator.addListener(object: Animator.AnimatorListener{
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationEnd(p0: Animator?) {
            onFinish?.invoke()
        }
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationStart(p0: Animator?) {}
    })
    animator.setDuration(duration)
    animator.start()
}

fun View.expand(duration: Long){
    val targetWith = (measuredWidth / 0.2).toInt()
    val animator = ValueAnimator.ofInt(measuredWidth, targetWith)
    animator.addUpdateListener {
        val v = it.animatedValue as Int
        val params = layoutParams
        params.width = v
        layoutParams = params
    }
    animator.addListener(object : Animator.AnimatorListener{
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationEnd(p0: Animator?) {
            val p = layoutParams
            p.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams = p
        }
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationStart(p0: Animator?) {}
    })
    animator.setDuration(duration)
    animator.start()
}

fun Context.showDialog(@LayoutRes layout: Int, setView: (AlertDialog, View) -> Unit, cancelable: Boolean = false){
    val v = LayoutInflater.from(this).inflate(layout, null ,false)
    AlertDialog.Builder(this)
        .setView(v)
        .setCancelable(cancelable)
        .create().also { dialog ->
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialog, v)
            dialog.show()
        }
}