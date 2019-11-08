package com.example.flxrexample.ranking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.activity_ranking_popup.*

class RankingPopupActivity : AppCompatActivity() {


    private lateinit var rankingListViewAdapter: RankingListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking_popup)

        rankingListViewAdapter = RankingListViewAdapter()

        ranking_popup_ranking_list.layoutManager = LinearLayoutManager(this)
        ranking_popup_ranking_list.adapter = rankingListViewAdapter

        rankingListViewAdapter.submitList(UserRepository.getUsers())

    }
}