package com.example.flxrexample.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.example.flxrexample.R
import com.example.flxrexample.charge.StarChargeActivity
import kotlinx.android.synthetic.main.activity_my_profile_star_history.*
import kotlinx.android.synthetic.main.my_profile_fragment.*
import com.synnapps.carouselview.ImageListener


class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private lateinit var viewModel: MyProfileViewModel
    val sampleImages: Array<Int> = arrayOf(
        R.drawable.sample_image,
        R.drawable.sample_image,
        R.drawable.sample_image,
        R.drawable.sample_image,
        R.drawable.sample_image
    )

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

        myprofile_history_btn.setOnClickListener {
            val historyIntent = Intent(activity, MyProfileHistoryActivity::class.java)
            startActivity(historyIntent)
        }

        myprofile_help_btn.setOnClickListener {
            val helpIntent = Intent(activity, MyProfileHelpActivity::class.java)
            startActivity(helpIntent)
        }

        myprofile_star_btn.setOnClickListener {
            val starHistoryIntent = Intent(activity, MyProfileStarHistoryActivity::class.java)
            startActivity(starHistoryIntent)
        }

        myprofile_ad_carousel_view.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }

        myprofile_ad_carousel_view.pageCount = sampleImages.size
    }

}
