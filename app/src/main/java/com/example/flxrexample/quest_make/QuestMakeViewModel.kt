package com.example.flxrexample.quest_make

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestConstraint
import com.example.flxrexample.quest_model.QuestRepository
import com.example.flxrexample.quest_model.QuestViewItem
import java.util.Map

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

class QuestMakeViewModel : ViewModel() {

    private val repository = QuestRepository()

    private val questConstraintLiveData = MutableLiveData<ArrayList<QuestConstraint>>()
    private val imageURLLiveData = MutableLiveData<String>()

    init {
        questConstraintLiveData.value = ArrayList()
    }

    fun getAllQuestConstraint() = questConstraintLiveData

    fun addQuestConstraint(questConstraint: QuestConstraint) {
        questConstraintLiveData.value?.add(questConstraintLiveData.value?.size!!,questConstraint)
        questConstraintLiveData.notifyObserver()
    }

    fun addQuestConstraintImageURL(questConstraint: QuestConstraint, url: String){
        val idx = questConstraintLiveData.value?.indexOf(questConstraint)!!

        val newItem = QuestConstraint(
            questConstraint.constraintNum,
            questConstraint.content,
            questConstraint.isCompleted,
            url,
            questConstraint.questID,
            questConstraint.id)

        questConstraintLiveData.value?.set(idx,newItem)
        questConstraintLiveData.notifyObserver()
    }

    fun addQuestConstraintContent(questConstraint: QuestConstraint, content: kotlin.collections.Map.Entry<Int, String>){
        val oldItem = questConstraintLiveData.value?.get(content.key)!!
        val newItem = QuestConstraint(
            oldItem.constraintNum,
            content.value,
            oldItem.isCompleted,
            oldItem.pictureURL,
            oldItem.questID,
            oldItem.id)

        questConstraintLiveData.value?.set(content.key, newItem)
        questConstraintLiveData.notifyObserver()
    }

    fun removeLastQuestConstraint(){
        if(questConstraintLiveData.value?.isNotEmpty()!!)
            questConstraintLiveData.value?.remove(questConstraintLiveData.value?.last())
        questConstraintLiveData.notifyObserver()
    }

    fun getImageURL() : MutableLiveData<String> {
        return imageURLLiveData
    }

    fun setImageURL(url: String){
        imageURLLiveData.postValue(url)
    }

    fun addQuest(quest: Quest) {

        /*
        val newConstraints = ArrayList<QuestConstraint>()

        questConstraintLiveData?.value!!.forEach {
           val item = QuestConstraint(
               it.constraintNum,
               it.content,
               it.isCompleted,
               it.pictureURL,
               quest.id,
               it.id
           )
            newConstraints.add(item)
        }

        questConstraintLiveData?.postValue(newConstraints)
        */

        val questViewItem = QuestViewItem(quest,questConstraintLiveData?.value!!.toList(),listOf())
        repository.addQuestViewItem(questViewItem)
    }
}
