package com.kes.app032_kt_viewpager.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kes.app032_kt_viewpager.MainActivity
import com.kes.app032_kt_viewpager.fragments.FirstFragment
import com.kes.app032_kt_viewpager.fragments.SecondFragment
import com.kes.app032_kt_viewpager.fragments.ThirdFragment

class FragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle)  {

    private val fragments: ArrayList<Fragment> = ArrayList()

    init {
        fragments.add(FirstFragment())
        fragments.add(SecondFragment())
        fragments.add(ThirdFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int = fragments.size
}