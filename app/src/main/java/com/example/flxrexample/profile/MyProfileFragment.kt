package com.example.flxrexample.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.flxrexample.R
import com.example.flxrexample.charge.StarChargeActivity
import com.example.flxrexample.coupon.CouponActivity
import com.example.flxrexample.databinding.MyProfileFragmentBinding
import com.example.flxrexample.quest_model.StarAccount
import kotlinx.android.synthetic.main.activity_my_profile_star_history.*
import kotlinx.android.synthetic.main.my_profile_fragment.*
import com.synnapps.carouselview.ImageListener


class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private lateinit var binding: MyProfileFragmentBinding
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
        binding = DataBindingUtil.inflate(inflater,R.layout.my_profile_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)

        binding.myprofileStarChargeBtn.setOnClickListener {
            val starChargeIntent = Intent(activity, StarChargeActivity::class.java)
            startActivity(starChargeIntent)
        }

        binding.myprofileHistoryBtn.setOnClickListener {
            val historyIntent = Intent(activity, MyProfileHistoryActivity::class.java)
            startActivity(historyIntent)
        }

        binding.myprofileHelpBtn.setOnClickListener {
            val helpIntent = Intent(activity, MyProfileHelpActivity::class.java)
            startActivity(helpIntent)
        }

        binding.myprofileStarBtn.setOnClickListener {
            val starHistoryIntent = Intent(activity, MyProfileStarHistoryActivity::class.java)
            startActivity(starHistoryIntent)
        }

        binding.myprofileCouponBtn.setOnClickListener {
            val couponIntent = Intent(activity, CouponActivity::class.java)
            startActivity(couponIntent)
        }

        binding.myprofileAdCarouselView.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }

        binding.myprofileAdCarouselView.pageCount = sampleImages.size

        viewModel.getStarAccount().observe(this, Observer<StarAccount> { starAccount ->
            binding.myprofileHaveStarText.text = "${starAccount.starAmount} 스타 보유중"
        })
    }

}
