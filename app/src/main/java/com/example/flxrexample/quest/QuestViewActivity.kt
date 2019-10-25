package com.example.flxrexample.quest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import com.example.flxrexample.quest.QuestListRepository.QuestListFactory
import kotlinx.android.synthetic.main.activity_quest_view.*
import kotlinx.android.synthetic.main.quest_main_fragment.*

class QuestViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_view)

        quest_view_condition_list.layoutManager = LinearLayoutManager(this)
        quest_view_condition_list.addItemDecoration(SpacingItemDecorator(1,20))
        quest_view_condition_list.adapter = QuestConstraintListViewAdapter(QuestListFactory())
        quest_view_condition_list.setHasFixedSize(true)

        quest_view_review_list.layoutManager = LinearLayoutManager(this)
        quest_view_review_list.addItemDecoration(SpacingItemDecorator(1,20))
        quest_view_review_list.adapter = QuestReviewListViewAdapter(QuestListFactory())
        quest_view_review_list.setHasFixedSize(true)
    }
}
