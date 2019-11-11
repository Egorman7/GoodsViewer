package yehor.tkachuk.goodsviewer.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_good_detail.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.utils.toast
import yehor.tkachuk.goodsviewer.viewmodel.ListFragmentViewModel
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel
import java.io.File

class ListDetailFragment : BaseFragment<ListFragmentViewModel>(ListFragmentViewModel::class){
    companion object{
        private const val KEY_GOOD_ID = "id"
        private const val KEY_GOOD_TITLE = "title"
        private const val KEY_GOOD_TEXT = "text"
        private const val KEY_GOOD_IMG = "img"
        private const val KEY_LOGGED_IN = "loggedIn"

        fun createInstance(good: Good, loggedIn: Boolean): ListDetailFragment{
            return ListDetailFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt(KEY_GOOD_ID, good.id)
                    bundle.putString(KEY_GOOD_TITLE, good.title)
                    bundle.putString(KEY_GOOD_TEXT, good.text)
                    bundle.putString(KEY_GOOD_IMG, good.getImageUrl().toString())
                    bundle.putBoolean(KEY_LOGGED_IN, loggedIn)
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
            val title = args.getString(KEY_GOOD_TITLE)
            val text = args.getString(KEY_GOOD_TEXT)
            val img = args.getString(KEY_GOOD_IMG)
            val id = args.getInt(KEY_GOOD_ID)
            val good = Good(id, img.substringAfterLast("/"), text, title)
            good_detail_title.text = title
            good_detail_text.text = text
            Glide.with(good_detail_image)
                .load(Uri.parse(img))
                .error(Glide.with(good_detail_image)
                    .load(File(context?.filesDir, good.getLocalImage()))
                    .error(R.drawable.ic_broken_image))
                .placeholder(R.drawable.ic_broken_image)
                .into(good_detail_image)
            good_detail_button_save.setOnClickListener {
                sharedViewModel.saveGood(good)
            }
            if(!args.getBoolean(KEY_LOGGED_IN)){
                good_detail_button_save.visibility = View.GONE
            }
        }
        sharedViewModel.saveResult.observe(viewLifecycleOwner, Observer { result ->
            context?.toast(result.message)
        })
    }
}