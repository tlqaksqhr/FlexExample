package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.MainActivity
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityQuestOngoingReviewBinding
import com.example.flxrexample.quest_model.*
import kotlinx.android.synthetic.main.activity_quest_ongoing_review.*

class QuestOngoingReviewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityQuestOngoingReviewBinding
    private lateinit var viewModel: QuestOngoingReviewViewModel

    private lateinit var questReviewPictureListViewAdapter: QuestReviewPictureListViewAdapter

    private lateinit var starAccount: StarAccount

    private lateinit var quest: Quest

    private var questID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestOngoingReviewViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityQuestOngoingReviewBinding>(this,
            R.layout.activity_quest_ongoing_review)

        questReviewPictureListViewAdapter = QuestReviewPictureListViewAdapter()

        binding.questOngoingReviewPictureList.layoutManager = LinearLayoutManager(this)
        binding.questOngoingReviewPictureList.adapter = questReviewPictureListViewAdapter
        questReviewPictureListViewAdapter.submitList(QuestListFactory.questAuthItemFactory())

        questID = intent.extras?.getInt("id")!!

        viewModel.getQuest(questID).observe(this, Observer<Quest> { quest ->
            this.quest = quest
            binding.questOngoingReviewRegisterBtn.text = "등록 (${quest.questStar*2}스타)"
            binding.questOngoingReviewCompleteBtn.text = "완료 (${quest.questStar}스타)"
        })

        viewModel.getStarAccount().observe(this, Observer<StarAccount> { starAccount ->
            this.starAccount = starAccount
        })

        viewModel.getQuestAuthImages(questID).observe(this, Observer<List<QuestAuthImage>> { questAuthImages ->
            questReviewPictureListViewAdapter.submitList(questAuthImages)
        })

        binding.questOngoingReviewRegisterBtn.setOnClickListener {

            val review = Review(
                binding.questOngoingReviewEditbox.text.toString(),
                binding.questOngoingReviewRatingbar.rating.toFloat(),
                false,
                questID
            )

            viewModel.addReview(review)
            viewModel.updateQuest(Quest(
                quest.title,
                quest.desc,
                quest.isCompleted,
                quest.challengingCount,
                quest.totalStar,
                quest.questStar,
                quest.numOfComplete+1,
                quest.address,
                quest.latLng,
                quest.startDate,
                quest.endDate,
                quest.isFavorite,
                quest.isOngoing,
                quest.id
            ))
            viewModel.updateStarAccount(starAccount, quest.questStar*2)

            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.questOngoingReviewCompleteBtn.setOnClickListener {

            viewModel.updateQuest(Quest(
                quest.title,
                quest.desc,
                quest.isCompleted,
                quest.challengingCount,
                quest.totalStar,
                quest.questStar,
                quest.numOfComplete+1,
                quest.address,
                quest.latLng,
                quest.startDate,
                quest.endDate,
                quest.isFavorite,
                quest.isOngoing,
                quest.id
            ))
            viewModel.updateStarAccount(starAccount,quest.questStar)
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
