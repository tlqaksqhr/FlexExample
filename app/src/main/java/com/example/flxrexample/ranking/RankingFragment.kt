package com.example.flxrexample.ranking

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.flxrexample.R
import kotlinx.android.synthetic.main.ranking_fragment.*

class RankingFragment : Fragment() {

    companion object {
        fun newInstance() = RankingFragment()
    }

    private lateinit var viewModel: RankingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ranking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RankingViewModel::class.java)


        ranking_seoul_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","서울")
            startActivity(intent)
        }

        ranking_inchon_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","인천")
            startActivity(intent)
        }

        ranking_gyunggi_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","경기도")
            startActivity(intent)

        }

        ranking_gangwon_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","강원도")
            startActivity(intent)

        }

        ranking_chungbuk_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","충청북도")
            startActivity(intent)

        }

        ranking_chungnam_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","충청남도")
            startActivity(intent)

        }

        ranking_daejun_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","대전")
            startActivity(intent)

        }

        ranking_gyungbuk_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","경상북도")
            startActivity(intent)

        }

        ranking_daegu_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","대구")
            startActivity(intent)

        }

        ranking_gyungnam_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","경상남도")
            startActivity(intent)

        }

        ranking_chynkbuk_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","전라북도")
            startActivity(intent)

        }

        ranking_chynknam_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","전라남도")
            startActivity(intent)

        }

        ranking_ulsan_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","울산")
            startActivity(intent)

        }

        ranking_busan_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","부산")
            startActivity(intent)

        }

        ranking_jeju_btn.setOnClickListener {
            val intent = Intent(this.context, RankingPopupActivity::class.java)
            intent.putExtra("CityName","제주도")
            startActivity(intent)

        }

    }

}
