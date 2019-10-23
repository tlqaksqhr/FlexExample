package com.example.flxrexample.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.flxrexample.R
import com.example.flxrexample.charge.StarChargeActivity
import kotlinx.android.synthetic.main.my_profile_fragment.*

class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)

        myprofile_star_charge_btn.setOnClickListener {
            val starChargeIntent = Intent(activity, StarChargeActivity::class.java)
            startActivity(starChargeIntent)
        }
    }

}
