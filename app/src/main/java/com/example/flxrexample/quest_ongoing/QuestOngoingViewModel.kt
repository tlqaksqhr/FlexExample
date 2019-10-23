package com.example.flxrexample.quest_ongoing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class QuestOngoingViewModel : ViewModel() {
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

    val ongoingQuestCount: Int
        get() = _liveData.value?.ongoingQuests!!.size

    init {
        _liveData.value = Container(
            listOf(
                OngoingQuest(
                    OngoingQuestHeader(title = "제주로, 해녀로", isCompleted = false, numOfComplete = 10),
                    listOf(
                        QuestConstraint(1, "해녀 슈트 입고 인증사진 찍기", false),
                        QuestConstraint(2, "바다속 사진 찍기", true),
                        QuestConstraint(3, "인증사진 찍기", false)
                    ),
                    OngoingQuestFooter()
                ),
                OngoingQuest(
                    OngoingQuestHeader(title = "오늘 부터 1일", isCompleted = false, numOfComplete = 10),
                    listOf(
                        QuestConstraint(1, "해녀 슈트 입고 인증사진 찍기", false),
                        QuestConstraint(2, "바다속 사진 찍기", true),
                        QuestConstraint(3, "인증사진 찍기", false)
                    ),
                    OngoingQuestFooter()
                )
            ),
            ongoingQuestHeaderExpanded
        )
    }
}
