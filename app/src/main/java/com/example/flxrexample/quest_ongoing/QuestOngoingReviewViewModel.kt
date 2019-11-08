package com.example.flxrexample.quest_ongoing

import androidx.lifecycle.ViewModel
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.Review

class QuestOngoingReviewViewModel : ViewModel() {

    private val repository = QuestRepository()

    fun addReview(review: Review) = repository.addReview(review)
}