package com.example.flxrexample.quest_ongoing

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flxrexample.quest_model.OngoingQuestAuthClick
import com.example.flxrexample.quest_model.OngoingQuestAuthCompleteClick

class QuestOngoingViewModelFactory(
    val mOngoingQuestAuthClick: OngoingQuestAuthClick,
    val mOngoingQuestAuthCompleteClick : OngoingQuestAuthCompleteClick) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestOngoingViewModel(mOngoingQuestAuthClick, mOngoingQuestAuthCompleteClick) as T
    }

}