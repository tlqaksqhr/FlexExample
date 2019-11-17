package com.example.flxrexample.quest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.QuestViewItem

class QuestViewViewModel : ViewModel() {

    private val repository = QuestRepository()

    fun getQuestViewItem(id: Int) = repository.getQuestViewItem(id)

    fun getQuestReviews(id: Int) = repository.getQuestReviews(id)

    fun updateQuest(quest: Quest) = repository.updateQuest(quest)

}
