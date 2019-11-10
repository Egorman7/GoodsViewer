package yehor.tkachuk.goodsviewer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_main_list.view.*
import kotlinx.android.synthetic.main.item_main_list_collapsed.view.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.model.Good

class GoodsListAdapter : RecyclerView.Adapter<GoodsListAdapter.ViewHolder>(){
    companion object{
        const val TYPE_EXPANDED = 1
        const val TYPE_COLLAPSED = 2
    }
    private val list = mutableListOf<Good>()
    private var currentViewType = TYPE_EXPANDED
    private var selectedItem = -1

    var onItemClicked: ((Good, Int) -> Unit)? = null

    fun setData(data: List<Good>){
        selectedItem = -1
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun isCollapsed(): Boolean{
        return currentViewType == TYPE_COLLAPSED
    }

    fun collapse(){
        currentViewType = TYPE_COLLAPSED
        notifyDataSetChanged()
    }

    fun expand(){
        currentViewType = TYPE_EXPANDED
        notifyDataSetChanged()
    }

    private fun selectItem(index: Int){
        val lastSelected = selectedItem
        selectedItem = index
        if(lastSelected != -1){
            notifyItemChanged(lastSelected)
        }
        notifyItemChanged(selectedItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            TYPE_EXPANDED -> ExpandedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false))
            TYPE_COLLAPSED -> CollapsedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_list_collapsed, parent, false))
            else -> throw IllegalStateException("No such view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentViewType
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    abstract inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        open fun bind(item: Good, position: Int){
            itemView.setOnClickListener {
                onItemClicked?.invoke(item, position)
                selectItem(position)
            }
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (position == selectedItem) {
                        R.color.colorSelectedItemBackground
                    } else {
                        R.color.colorBackgroundLight}))
        }
    }

    inner class ExpandedViewHolder(itemView: View) : ViewHolder(itemView){
        override fun bind(item: Good, position: Int) {
            super.bind(item, position)
            itemView.item_main_list_title.text = item.title
            itemView.item_main_list_text.text = item.text
            Glide.with(itemView.context)
                .load(item.getImageUrl())
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.ic_broken_image)
                .centerCrop()
                .into(itemView.item_main_list_image)
        }
    }

    inner class CollapsedViewHolder(itemView: View) : ViewHolder(itemView){
        override fun bind(item: Good, position: Int) {
            super.bind(item, position)
            Glide.with(itemView.context)
                .load(item.getImageUrl())
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.ic_broken_image)
                .centerCrop()
                .into(itemView.item_main_list_collapsed_image)
        }
    }
}