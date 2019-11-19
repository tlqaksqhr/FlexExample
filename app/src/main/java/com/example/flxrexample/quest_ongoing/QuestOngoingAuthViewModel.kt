package com.example.flxrexample.quest_ongoing

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flxrexample.app.DataQuestApplication
import com.example.flxrexample.quest_make.notifyObserver
import com.example.flxrexample.quest_model.QuestAuthImage
import com.example.flxrexample.quest_model.QuestConstraint
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.RemoteRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class QuestOngoingAuthViewModel : ViewModel() {

    private val repository = QuestRepository()

    private val questAuthImageLiveData = MutableLiveData<ArrayList<QuestAuthImage>>()
    private val imageURLLiveData = MutableLiveData<String>()

    init {
        questAuthImageLiveData.value = ArrayList()
    }

    fun getAllAuthImageLiveData() = questAuthImageLiveData

    fun getQuestConstraints(id: Int) = repository.getQuestConstraints(id)

    fun getQuestConstraintsCount(id: Int) = repository.getQuestConstraintsCount(id)

    fun addQuestAuthImage(questAuthImage: QuestAuthImage) {
        questAuthImageLiveData.value?.add(questAuthImageLiveData.value?.size!!,questAuthImage)
        questAuthImageLiveData.notifyObserver()
    }

    fun addQuestAuthImageURL(questAuthImage: QuestAuthImage, url: String) {
        val idx = questAuthImageLiveData.value?.indexOf((questAuthImage))!!

        val newItem = QuestAuthImage(
            url,
            questAuthImage.questID,
            questAuthImage.id)

        questAuthImageLiveData.value?.set(idx, newItem)
        questAuthImageLiveData.notifyObserver()
    }

    fun addQuestAuthImages(id: Int, questConstraints: List<QuestConstraint>) {

        val questAuthImages = questAuthImageLiveData?.value!!
        val newItems = ArrayList<QuestAuthImage>()

        var cnt = 0

        questAuthImages.forEach {
            newItems.add(QuestAuthImage(
                it.pictureURL,
                questConstraints[cnt].id,
                it.id
            ))
            cnt += 1
        }
        repository.addQuestAuthImages(newItems.toList())
    }

    fun getImageURL() : MutableLiveData<String> {
        return imageURLLiveData
    }

    fun setImageURL(url: String){
        imageURLLiveData.postValue(url)
    }
}