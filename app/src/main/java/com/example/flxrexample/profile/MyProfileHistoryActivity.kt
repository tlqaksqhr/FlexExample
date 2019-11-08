package com.example.flxrexample.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.activity_my_profile_history.*

class MyProfileHistoryActivity : AppCompatActivity() {

    private lateinit var historyListViewAdapter: HistoryListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_history)

        historyListViewAdapter = HistoryListViewAdapter()

        myprofile_history_list.layoutManager = LinearLayoutManager(this)
        myprofile_history_list.adapter = historyListViewAdapter

        historyListViewAdapter.submitList(HistoryRepository.getHistories())
    }
}
