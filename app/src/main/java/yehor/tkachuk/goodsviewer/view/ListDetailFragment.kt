package yehor.tkachuk.goodsviewer.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_good_detail.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class ListDetailFragment : BaseFragment<MainViewModel>(MainViewModel::class){
    companion object{
        private const val KEY_GOOD_ID = "id"
        private const val KEY_GOOD_TITLE = "title"
        private const val KEY_GOOD_TEXT = "text"
        private const val KEY_GOOD_IMG = "img"

        fun createInstance(good: Good): ListDetailFragment{
            return ListDetailFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt(KEY_GOOD_ID, good.id)
                    bundle.putString(KEY_GOOD_TITLE, good.title)
                    bundle.putString(KEY_GOOD_TEXT, good.text)
                    bundle.putString(KEY_GOOD_IMG, good.getImageUrl().toString())
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_good_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.also { args ->
            good_detail_title.text = args.getString(KEY_GOOD_TITLE)
            good_detail_text.text = args.getString(KEY_GOOD_TEXT)
            Glide.with(good_detail_image)
                .load(Uri.parse(args.getString(KEY_GOOD_IMG)))
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.ic_broken_image)
                .into(good_detail_image)
        }
    }
}