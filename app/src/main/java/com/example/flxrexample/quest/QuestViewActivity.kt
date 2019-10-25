package com.example.flxrexample.quest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityQuestViewBinding
import com.example.flxrexample.quest_model.QuestViewItem
import kotlinx.android.synthetic.main.activity_quest_view.*
import kotlinx.android.synthetic.main.quest_main_fragment.*

class QuestViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestViewBinding
    private lateinit var viewModel: QuestViewViewModel
    private lateinit var questConstraintAdapter: QuestConstraintListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestViewViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityQuestViewBinding>(this,R.layout.activity_quest_view)

        questConstraintAdapter = QuestConstraintListViewAdapter()

        binding.questViewConditionList.layoutManager = LinearLayoutManager(this)
        binding.questViewConditionList.addItemDecoration(SpacingItemDecorator(1,20))
        binding.questViewConditionList.setHasFixedSize(true)
        binding.questViewConditionList.adapter = questConstraintAdapter
        questConstraintAdapter.submitList(QuestListFactory.questConstraintFactory())

        binding.questViewReviewList.layoutManager = LinearLayoutManager(this)
        binding.questViewReviewList.addItemDecoration(SpacingItemDecorator(1,20))
        binding.questViewReviewList.setHasFixedSize(true)
        binding.questViewReviewList.adapter = QuestReviewListViewAdapter(QuestListFactory.questListFactory())

        //viewModel.getQuestViewItem(1).observe(this, Observer<QuestViewItem>{ questViewItem ->
        //    questConstraintAdapter.submitList(questViewItem.questConstraints)
        //})
    }
}
