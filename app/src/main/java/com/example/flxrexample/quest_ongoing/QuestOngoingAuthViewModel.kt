package com.example.flxrexample.quest_ongoing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flxrexample.quest_make.notifyObserver
import com.example.flxrexample.quest_model.QuestAuthImage
import com.example.flxrexample.quest_model.QuestRepository

class QuestOngoingAuthViewModel : ViewModel() {

    private val repository = QuestRepository()

    private val questAuthImageLiveData = MutableLiveData<ArrayList<QuestAuthImage>>()
    private val imageURLLiveData = MutableLiveData<String>()

    init {
        questAuthImageLiveData.value = ArrayList()
    }

    fun getAllAuthImageLiveData() = questAuthImageLiveData

    fun addQuestAuthImageURL(questAuthImage: QuestAuthImage, url: String) {
        val idx = questAuthImageLiveData.value?.indexOf((questAuthImage))!!

        val newItem = QuestAuthImage(url)

        questAuthImageLiveData.value?.set(idx, newItem)
        questAuthImageLiveData.notifyObserver()
    }


    fun getImageURL() : MutableLiveData<String> {
        return imageURLLiveData
    }

    fun setImageURL(url: String){
        imageURLLiveData.postValue(url)
    }
}