package com.example.flxrexample.charge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.StarAccount

class StarChargeViewModel : ViewModel() {

    private val repository = QuestRepository()
    private val addedStar = MutableLiveData<Int>()

    fun getStarAccount() = repository.getStarAccount()

    fun getAddedStar() = addedStar

    fun setAddedStar(stars: Int) {
        addedStar.postValue(stars)
    }

    fun updateStarAccount(starAccount: StarAccount, price: Int) {
        val newItem = StarAccount(starAccount.id,price,starAccount.userID)
        repository.updateStarAccount(newItem)
    }
}