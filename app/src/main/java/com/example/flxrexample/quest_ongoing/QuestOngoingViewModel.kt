package com.example.flxrexample.quest_ongoing

import android.content.Intent
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.flxrexample.quest_model.*
import android.view.ViewGroup.MarginLayoutParams



class QuestOngoingViewModel(val ongoingQuestAuthClick: OngoingQuestAuthClick) : ViewModel() {
    private val repository: QuestRepository = QuestRepository()

    val ongoingQuestLiveData: LiveData<Container>
        get() = _liveData
    private val _liveData = MutableLiveData<Container>()

    private val ongoingQuestHeaderExpanded: OngoingQuestHeaderExpanded = { ongoingQuestHeader: OngoingQuestHeader ->
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
}
