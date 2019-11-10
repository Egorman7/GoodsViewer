package yehor.tkachuk.goodsviewer.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DetailPagerAdapter(fragmentManager: FragmentManager,
                         private val titles: List<String>) : FragmentPagerAdapter(fragmentManager){
    private val fragments = mutableListOf<Fragment>()

    fun setFragments(newFragments: List<Fragment>){
        fragments.clear()
        fragments.addAll(newFragments)
        notifyDataSetChanged()
    }

    fun clear(){
        if(fragments.isNotEmpty()) {
            fragments.clear()
            notifyDataSetChanged()
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}