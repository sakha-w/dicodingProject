package org.d3if3102.dicoding.ui.user

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserDetailAdapter(
    fg: FragmentActivity,
    private val fragment: MutableList<Fragment>
) : FragmentStateAdapter(fg) {
    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]
}