package com.example.flxrexample.quest_ongoing

import java.util.concurrent.atomic.AtomicInteger

private val globalId = AtomicInteger(1)

typealias OngoingQuestHeaderExpanded = (quest: OngoingQuestHeader) -> Unit
typealias FavoriteQuestListExpanded = (favorite: FavoriteQuest) -> Unit

data class Container(
    val ongoingQuests: List<OngoingQuest>,
    val ongoingQuestHeaderExpanded: OngoingQuestHeaderExpanded
)

data class OngoingQuestHeader(
    val isExpanded: Boolean = false,
    val title: String,
    val isCompleted: Boolean,
    val numOfComplete: Int,
    val id: Int = globalId.getAndIncrement()
)

data class OngoingQuest(
    val ongoingQuestHeader: OngoingQuestHeader,
    val questConstraints: List<QuestConstraint>,
    val ongoingQuestFooter: OngoingQuestFooter
)

data class OngoingQuestFooter(
    val id: Int = globalId.getAndIncrement()
)

data class FavoriteHeader(
    val isExpanded: Boolean = false,
    val title: String,
    val desc: String,
    val isCompleted: Boolean,
    val numOfComplete: Int,
    val id: Int = globalId.getAndIncrement()
)

data class FavoriteQuest(
    val favoriteHeader: FavoriteHeader,
    val questConstraints: List<QuestConstraint>
)

data class QuestConstraint(
    val constraintNum: Int,
    val content: String,
    val isCompleted: Boolean,
    val id: Int = globalId.getAndIncrement()
)