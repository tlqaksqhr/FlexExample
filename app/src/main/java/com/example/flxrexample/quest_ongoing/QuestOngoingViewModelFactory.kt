package com.example.flxrexample.quest_ongoing

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flxrexample.quest_model.OngoingQuestAuthClick

class QuestOngoingViewModelFactory(
    val mOngoingQuestAuthClick: OngoingQuestAuthClick) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestOngoingViewModel(mOngoingQuestAuthClick) as T
    }

}