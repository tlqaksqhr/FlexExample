package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.activity_quest_ongoing_review.*

class QuestOngoingReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_ongoing_review)

        quest_ongoing_review_picture_list.layoutManager = LinearLayoutManager(this)
        quest_ongoing_review_picture_list.adapter = QuestReviewPictureListViewAdapter(mutableListOf(1,2,3,4,5,6))
    }
}
