package com.example.flxrexample.quest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.MainActivity
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityQuestViewBinding
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestListFactory
import com.example.flxrexample.quest_model.QuestViewItem
import com.example.flxrexample.quest_ongoing.QuestOngoingAuthActivity
import com.squareup.picasso.Picasso

class QuestViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestViewBinding
    private lateinit var viewModel: QuestViewViewModel
    private lateinit var questConstraintAdapter: QuestConstraintListViewAdapter
    private lateinit var questReviewListAdapter: QuestReviewListViewAdapter

    private var questID: Int = 0
    private lateinit var quest: Quest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestViewViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityQuestViewBinding>(this,R.layout.activity_quest_view)

        questID = intent.extras?.getInt("id")!!

        questConstraintAdapter = QuestConstraintListViewAdapter()
        questReviewListAdapter = QuestReviewListViewAdapter()

        binding.questViewConditionList.layoutManager = LinearLayoutManager(this)
        binding.questViewConditionList.addItemDecoration(SpacingItemDecorator(1,30))
        binding.questViewConditionList.setHasFixedSize(true)
        binding.questViewConditionList.adapter = questConstraintAdapter
        questConstraintAdapter.submitList(QuestListFactory.questConstraintFactory())

        binding.questViewReviewList.layoutManager = LinearLayoutManager(this)
        binding.questViewReviewList.addItemDecoration(SpacingItemDecorator(1,30))
        binding.questViewReviewList.setHasFixedSize(true)
        binding.questViewReviewList.adapter = questReviewListAdapter
        questReviewListAdapter.submitList(QuestListFactory.questReviewFactory())

        binding.questViewChallengeBtn.setOnClickListener {
            viewModel.updateQuest(Quest(
                quest.title,
                quest.desc,
                quest.isCompleted,
                quest.challengingCount,
                quest.totalStar,
                quest.questStar,
                quest.numOfComplete,
                quest.address,
                quest.latLng,
                quest.startDate,
                quest.endDate,
                quest.isFavorite,
                true,
                quest.id
            ))

            val intent = Intent(this,QuestOngoingAuthActivity::class.java)
            intent.putExtra("id",questID)
            startActivity(intent)
        }


        binding.questViewCancelBtn.setOnClickListener {
            viewModel.updateQuest(Quest(
                quest.title,
                quest.desc,
                quest.isCompleted,
                quest.challengingCount,
                quest.totalStar,
                quest.questStar,
                quest.numOfComplete,
                quest.address,
                quest.latLng,
                quest.startDate,
                quest.endDate,
                quest.isFavorite,
                false,
                quest.id
            ))

            startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.getQuestViewItem(questID).observe(this, Observer<QuestViewItem>{ questViewItem ->
            if(questViewItem != null) {
                binding.apply {
                    this.questViewPageTitle.text = questViewItem.quest.title
                    this.questViewPageCount.text = "${questViewItem.quest.challengingCount} 명 도전 중"
                    this.questViewLocationText.text = questViewItem.quest.address
                    this.questViewContentText.text = questViewItem.quest.desc
                    this.questViewDateText.text = "${questViewItem.quest.startDate} ~ ${questViewItem.quest.endDate}"

                    var avgRating = 0.0
                    val totalGiveStar = questViewItem.quest.numOfComplete * questViewItem.quest.questStar
                    val progress = (totalGiveStar.toFloat() / questViewItem.quest.totalStar.toFloat()) * 100
                    val totalCount = questViewItem.quest.totalStar / questViewItem.quest.questStar

                    questViewItem.questReviews.forEach {
                        avgRating += it.rating
                    }

                    if(questViewItem.questReviews.size!=0)
                        avgRating /= questViewItem.questReviews.size

                    this.questViewReviewRatingCount.text = "${avgRating}"
                    this.questViewReviewRatingCountTotal.text = " /5.0"
                    this.questViewReviewCountBtn.text = "${questViewItem.questReviews.size}건"

                    this.questViewRewardStarCount.text = "${totalGiveStar}스타 지급"
                    this.questViewRewardStarCountRemaining.text = " /${totalCount - questViewItem.quest.numOfComplete}명 남음"
                    this.questViewRewardProgressBar.progress = progress.toInt()
                    this.questViewRewardProgressBarPercent.text = "${progress}%"

                    this.questViewContentImage.setImageListener { position, imageView ->
                        if(questViewItem.questConstraints[position].pictureURL.startsWith("https")==false) {
                            Picasso.get().load("file://${questViewItem.
                                questConstraints[position].pictureURL}").into(imageView)
                        }
                        else {
                            Picasso.get().load(questViewItem.questConstraints[position]
                                .pictureURL).into(imageView)
                        }
                    }

                    this.questViewContentImage.pageCount = questViewItem.questConstraints.size
                }

                questConstraintAdapter.submitList(questViewItem.questConstraints)
                questReviewListAdapter.submitList(questViewItem.questReviews)
                quest = questViewItem.quest
            }
        })
    }
}
