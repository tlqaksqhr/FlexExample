package com.example.flxrexample.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import com.example.flxrexample.quest.SpacingItemDecorator
import com.example.flxrexample.quest_ongoing.SpacingItemDecoratorBottom
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)

        val couponListAdapter = CouponListAdapter()
        val sampleItem = listOf("파라세일 성인 1인 입장권","쿠폰명","쿠폰명")

        coupon_list.layoutManager = LinearLayoutManager(this)
        coupon_list.adapter = couponListAdapter
        coupon_list.addItemDecoration(SpacingItemDecoratorBottom(1,20))

        couponListAdapter.submitList(sampleItem)
    }
}