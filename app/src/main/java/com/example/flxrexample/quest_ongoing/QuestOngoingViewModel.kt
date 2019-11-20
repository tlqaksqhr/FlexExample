package com.example.flxrexample.quest_ongoing

import android.content.Intent
import android.view.View
import androidx.databinding.BindingAdapter
import com.example.flxrexample.quest_model.*
import android.view.ViewGroup.MarginLayoutParams
import androidx.lifecycle.*


class QuestOngoingViewModel(val ongoingQuestAuthClick: OngoingQuestAuthClick) : ViewModel() {
    private val repository: QuestRepository = QuestRepository()

    val ongoingQuestLiveData: LiveData<Container>
        get() = _liveData
    private val _liveData = MutableLiveData<Container>()

    val ongoingQuestHeaderExpanded: OngoingQuestHeaderExpanded = { ongoingQuestHeader: OngoingQuestHeader ->
        val oldContainer = _liveData.value
        if (oldContainer != null) {
            val newOngoingQuests = oldContainer.ongoingQuests.map {
                if (it.ongoingQuestHeader.id == ongoingQuestHeader.id) {
                    it.copy(ongoingQuestHeader = it.ongoingQuestHeader.copy(isExpanded = !ongoingQuestHeader.isExpanded))
                } else {
                    it
                }
            }
            _liveData.value = oldContainer.copy(ongoingQuests = newOngoingQuests)
        }
    }

    private val favoriteQuestHeaderExpanded: FavoriteQuestHeaderExpanded = { favoriteQuestHeader: FavoriteQuestHeader ->
        val oldContainer = _liveData.value
        if (oldContainer != null) {
            val newFavoriteQuests = oldContainer.favoriteQuests.map {
                if (it.favoriteQuestHeader.id == favoriteQuestHeader.id) {
                    it.copy(favoriteQuestHeader = it.favoriteQuestHeader.copy(isExpanded = !favoriteQuestHeader.isExpanded))
                } else {
                    it
                }
            }

            _liveData.value = oldContainer.copy(favoriteQuests = newFavoriteQuests)
        }
    }

    private fun combineLatestData(liveData1: LiveData<List<Quest>>, liveData2: LiveData<List<QuestConstraint>>) : List<QuestViewItem>? {
        val questList = liveData1.value
        val questConstraintsList = liveData2.value

        if (questList == null || questConstraintsList == null){ return null }else {
            val newItemList = mutableListOf<QuestViewItem>()
            questList.forEach { quest ->
                newItemList.add(QuestViewItem(quest,
                    questConstraintsList.filter { it.questID == quest.id},
                    listOf())) }
            return newItemList
        }
    }

    fun getQuestViewItems() : MediatorLiveData<List<QuestViewItem>> {

        val liveData1: LiveData<List<Quest>> = repository.getQuests()
        val liveData2: LiveData<List<QuestConstraint>> = repository.getAllQuestConstraints()

        val result = MediatorLiveData<List<QuestViewItem>>()

        result.addSource(liveData1) { value ->
            result.value = combineLatestData(liveData1, liveData2)
        }

        result.addSource(liveData2) { value ->
            result.value = combineLatestData(liveData1, liveData2)
        }

        return result
    }

    fun mappingLiveData(questViewItems: List<QuestViewItem>){

        var ongoingQuestList = mutableListOf<OngoingQuest>()
        var favoriteQuestList = mutableListOf<FavoriteQuest>()

        questViewItems.forEach{questViewItem ->

            if(questViewItem.quest.isOngoing) {
                val ongoingQuest = OngoingQuest(
                    OngoingQuestHeader(
                        id = questViewItem.quest.id,
                        title = questViewItem.quest.title,
                        isCompleted = questViewItem.quest.isCompleted,
                        numOfComplete = questViewItem.quest.numOfComplete
                    ),
                    questViewItem.questConstraints,
                    OngoingQuestFooter(),
                    ongoingQuestAuthClick
                )
                ongoingQuestList.add(ongoingQuest)
            }

            if(questViewItem.quest.isFavorite){
                val favoriteQuest = FavoriteQuest(
                    FavoriteQuestHeader(
                        id = questViewItem.quest.id,
                        title = questViewItem.quest.title,
                        desc = questViewItem.quest.desc,
                        isCompleted = questViewItem.quest.isCompleted,
                        numOfComplete = questViewItem.quest.numOfComplete
                    ),
                    questViewItem.questConstraints,
                    FavoriteQuestFooter()
                )
                favoriteQuestList.add(favoriteQuest)
            }
        }

        _liveData.value =  Container(
            ongoingQuestList.toList(),
            ongoingQuestHeaderExpanded,
            favoriteQuestList.toList(),
            favoriteQuestHeaderExpanded
        )
    }

    /*
    init {
        _liveData.value = Container(
            listOf(
                OngoingQuest(
                    OngoingQuestHeader(
                        title = "제주로, 해녀로",
                        isCompleted = false,
                        numOfComplete = 10
                    ),
                    listOf(
                        QuestConstraint(1, "해녀 슈트 입고 인증사진 찍기", false,""),
                        QuestConstraint(2, "바다속 사진 찍기", true,""),
                        QuestConstraint(3, "인증사진 찍기", false,"")
                    ),
                    OngoingQuestFooter(),
                    ongoingQuestAuthClick
                ),
                OngoingQuest(
                    OngoingQuestHeader(
                        title = "오늘 부터 1일",
                        isCompleted = true,
                        numOfComplete = 10
                    ),
                    listOf(
                        QuestConstraint(1, "해녀 슈트 입고 인증사진 찍기", true,""),
                        QuestConstraint(2, "바다속 사진 찍기", true,""),
                        QuestConstraint(3, "인증사진 찍기", true,"")
                    ),
                    OngoingQuestFooter(),
                    ongoingQuestAuthClick
                )
            ),
            ongoingQuestHeaderExpanded,
            listOf(
                FavoriteQuest(
                    FavoriteQuestHeader(
                        title = "제주로, 해녀로",
                        desc = "바다속 풍경을 사진으로 찍고 인증하세요!",
                        isCompleted = false,
                        numOfComplete = 10
                    ),
                    listOf(
                        QuestConstraint(1, "해녀 슈트 입고 인증사진 찍기", false,""),
                        QuestConstraint(2, "바다속 사진 찍기", true,""),
                        QuestConstraint(3, "인증사진 찍기", false,"")
                    ),
                    FavoriteQuestFooter()
                ),
                FavoriteQuest(
                    FavoriteQuestHeader(
                        title = "오늘 부터 1일",
                        desc = "바다속 풍경을 사진으로 찍고 인증하세요!",
                        isCompleted = false,
                        numOfComplete = 10
                    ),
                    listOf(
                        QuestConstraint(1, "해녀 슈트 입고 인증사진 찍기", false,""),
                        QuestConstraint(2, "바다속 사진 찍기", false,""),
                        QuestConstraint(3, "인증사진 찍기", false,"")
                    ),
                    FavoriteQuestFooter()
                )
            ),
            favoriteQuestHeaderExpanded
        )
    }
     */
}
