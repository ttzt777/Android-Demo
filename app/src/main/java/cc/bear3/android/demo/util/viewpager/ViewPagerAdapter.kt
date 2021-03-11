package cc.bear3.android.util.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter<T : Fragment>(
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior) {
    private val fragmentWrapperList: MutableList<FragmentWrapper<T>> = mutableListOf()

    fun setFragments(titles: List<CharSequence>, fragments: List<T>) {
        if (titles.isEmpty() || fragments.isEmpty() || titles.size != fragments.size) {
            throw IllegalStateException("Titles must match fragments!")
        }

        fragmentWrapperList.clear()

        val count = titles.size
        for (index in 0 until count) {
            fragmentWrapperList.add(FragmentWrapper(titles[index], fragments[index]))
        }

        notifyDataSetChanged()
    }

    fun addFragment(title: CharSequence, fragment: T) {
        fragmentWrapperList.add(FragmentWrapper(title, fragment))
        notifyDataSetChanged()
    }

    fun updateFragmentTitle(position: Int, newTitle: CharSequence) {
        if (position < 0 || position >= fragmentWrapperList.size) {
            throw IndexOutOfBoundsException("Position out of fragmentWrapperList size")
        }

        fragmentWrapperList[position].title = newTitle
        notifyDataSetChanged()
    }

    fun updateFragmentTitles(newTitles: List<CharSequence>) {
        if (newTitles.size != fragmentWrapperList.size) {
            throw IndexOutOfBoundsException("New title array size does not match origin title array size")
        }

        val count = fragmentWrapperList.size

        for (index in 0 until count) {
            fragmentWrapperList[index].title = newTitles[index]
        }

        notifyDataSetChanged()
    }

    fun clear() {
        fragmentWrapperList.clear()

        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentWrapperList[position].title
    }

    override fun getItem(position: Int): T {
        return fragmentWrapperList[position].fragment
    }

    override fun getCount(): Int {
        return fragmentWrapperList.size
    }

    class FragmentWrapper<T : Fragment>(var title: CharSequence, var fragment: T)
}