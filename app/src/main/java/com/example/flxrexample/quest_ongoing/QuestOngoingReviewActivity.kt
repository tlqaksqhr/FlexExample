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
import com.example.flxrexample.quest_model.QuestAuthImage
import com.example.flxrexample.quest_model.QuestListFactory
import com.example.flxrexample.quest_model.Review
import com.example.flxrexample.quest_model.StarAccount
import kotlinx.android.synthetic.main.activity_quest_ongoing_review.*

class QuestOngoingReviewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityQuestOngoingReviewBinding
    private lateinit var viewModel: QuestOngoingReviewViewModel

    private lateinit var questReviewPictureListViewAdapter: QuestReviewPictureListViewAdapter

    private lateinit var starAccount: StarAccount

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
            viewModel.updateStarAccount(starAccount, 300)

            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.questOngoingReviewCompleteBtn.setOnClickListener {
            viewModel.updateStarAccount(starAccount,150)
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
