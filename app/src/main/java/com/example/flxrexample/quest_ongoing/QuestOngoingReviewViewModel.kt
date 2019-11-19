package com.example.flxrexample.quest_ongoing

import androidx.lifecycle.ViewModel
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.Review
import com.example.flxrexample.quest_model.StarAccount

class QuestOngoingReviewViewModel : ViewModel() {

    private val repository = QuestRepository()

    fun addReview(review: Review) = repository.addReview(review)

    fun getQuestAuthImages(id: Int) = repository.getQuestAuthImages(id)

    fun getStarAccount() = repository.getStarAccount()

    fun getQuest(id: Int) = repository.getQuest(id)

    fun updateQuest(quest: Quest) = repository.updateQuest(quest)

    fun updateStarAccount(starAccount: StarAccount,price: Int) {
        val totalStar = starAccount.starAmount + price
        val newItem = StarAccount(starAccount.id,totalStar,starAccount.userID)
        repository.updateStarAccount(newItem)
    }
}