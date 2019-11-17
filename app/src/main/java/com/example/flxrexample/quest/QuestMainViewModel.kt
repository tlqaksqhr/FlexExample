package com.example.flxrexample.quest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestRepository

class QuestMainViewModel : ViewModel() {

    private val repository = QuestRepository()
    private val allQuests = repository.getQuests()

    fun getQuests() = allQuests

    fun getQuest(id: Int) = repository.getQuest(id)

    fun updateQuest(quest: Quest) = repository.updateQuest(quest)
}
