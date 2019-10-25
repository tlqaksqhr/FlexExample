package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import com.example.flxrexample.app.DataQuestApplication

class QuestRepository{
    private val questDao: QuestDao = DataQuestApplication.database.questDao()
    //private val questConstraintDao: QuestConstraintDao = DataQuestApplication.database.questConstraintDao()
    private val allQuests: LiveData<List<Quest>>
    init{
        allQuests = questDao.getAllQuests()
    }

    fun getQuests(): LiveData<List<Quest>> = allQuests

    fun getQuest(id: Int) = questDao.getQuest(id)

    //fun getQuestViewItem(id: Int) = questDao.getQuestViewItem(id)
}