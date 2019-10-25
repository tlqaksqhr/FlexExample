package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.activity_quest_ongoing_auth.*

class QuestOngoingAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_ongoing_auth)

        quest_ongoing_auth_thumbnail_list.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        quest_ongoing_auth_thumbnail_list.adapter = QuestThumbListViewAdapter(mutableListOf(1,2,3,4,5,6))
        quest_ongoing_auth_thumbnail_list.addItemDecoration(SpacingItemDecoratorBottom(3,40))
        quest_ongoing_auth_thumbnail_list.setHasFixedSize(true)

        quest_ongoing_auth_submit_btn.setOnClickListener {
            val questReviewIntent = Intent(this, QuestOngoingReviewActivity::class.java)
            startActivity(questReviewIntent)
        }

    }
}
