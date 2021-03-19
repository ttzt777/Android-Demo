package cc.bear3.android.demo.util.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalArgumentException

/**
 *
 * @author TT
 * @since 2021-3-15
 */
class ViewPager2Adapter<T : Fragment>(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    private val titles = mutableListOf<CharSequence>()
    private val fragments = mutableListOf<T>()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    fun updateFragments(titles: List<CharSequence>, fragments: List<T>) {
        if (titles.size != fragments.size) {
            throw IllegalArgumentException("Title list size must equals fragment list size")
        }

        this.titles.clear()
        this.fragments.clear()

        this.titles.addAll(titles)
        this.fragments.addAll(fragments)

        notifyDataSetChanged()
    }

    fun getTitle(position: Int) : CharSequence {
        return if (position in 0 until titles.size) {
            titles[position]
        } else {
            ""
        }
    }
}