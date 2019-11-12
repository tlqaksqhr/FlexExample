package com.example.flxrexample.profile

import androidx.lifecycle.ViewModel;
import com.example.flxrexample.quest_model.QuestRepository

class MyProfileViewModel : ViewModel() {
    private val repository = QuestRepository()

    fun getStarAccount() = repository.getStarAccount()
}
