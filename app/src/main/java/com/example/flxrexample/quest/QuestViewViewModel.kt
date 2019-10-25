package com.example.flxrexample.quest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.QuestViewItem

class QuestViewViewModel : ViewModel() {

    private val repository = QuestRepository()

    //fun getQuestViewItem(id: Int) = repository.getQuestViewItem(id)

}
