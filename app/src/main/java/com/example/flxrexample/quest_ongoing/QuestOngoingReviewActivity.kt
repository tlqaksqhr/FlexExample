package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.MainActivity
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityQuestOngoingReviewBinding
import com.example.flxrexample.quest_model.Review
import kotlinx.android.synthetic.main.activity_quest_ongoing_review.*

class QuestOngoingReviewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityQuestOngoingReviewBinding
    private lateinit var viewModel: QuestOngoingReviewViewModel

    private var questID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestOngoingReviewViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityQuestOngoingReviewBinding>(this,
            R.layout.activity_quest_ongoing_review)

        binding.questOngoingReviewPictureList.layoutManager = LinearLayoutManager(this)
        binding.questOngoingReviewPictureList.adapter = QuestReviewPictureListViewAdapter(mutableListOf(1,2,3,4,5,6))

        questID = intent.extras?.getInt("id")!!

        quest_ongoing_review_register_btn.setOnClickListener {

            val review = Review(
                binding.questOngoingReviewEditbox.text.toString(),
                binding.questOngoingReviewRatingbar.rating.toFloat(),
                false,
                questID
            )

            viewModel.addReview(review)

            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
