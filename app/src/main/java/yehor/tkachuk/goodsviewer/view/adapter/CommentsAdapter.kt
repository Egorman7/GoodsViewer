package yehor.tkachuk.goodsviewer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_comment.view.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.model.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(){
    private val list = mutableListOf<Comment>()

    fun setComments(comments: List<Comment>){
        list.clear()
        list.addAll(comments)
        notifyDataSetChanged()
    }

    fun addComment(comment: Comment){
        list.add(comment)
        notifyItemChanged(list.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(comment: Comment){
            itemView.item_comment_user.text = comment.author.username
            itemView.item_comment_rating.rating = comment.rate.toFloat()
            itemView.item_comment_text.text = comment.text
        }
    }
}