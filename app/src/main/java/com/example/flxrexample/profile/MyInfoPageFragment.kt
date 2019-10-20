package com.example.flxrexample.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.flxrexample.R

class MyInfoPageFragment : Fragment() {

    companion object {
        fun newInstance() = MyInfoPageFragment()
    }

    private lateinit var viewModel: MyInfoPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_info_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyInfoPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
