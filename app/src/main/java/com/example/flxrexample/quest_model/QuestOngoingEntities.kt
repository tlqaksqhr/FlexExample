package com.example.flxrexample.quest_model

import java.util.concurrent.atomic.AtomicInteger

private val globalId = AtomicInteger(1)

typealias OngoingQuestHeaderExpanded = (quest: OngoingQuestHeader) -> Unit
typealias FavoriteQuestHeaderExpanded = (favorite: FavoriteQuestHeader) -> Unit
typealias OngoingQuestAuthClick = () -> Unit

data class Container(
    val ongoingQuests: List<OngoingQuest>,
    val ongoingQuestHeaderExpanded: OngoingQuestHeaderExpanded,
    val favoriteQuests: List<FavoriteQuest>,
    val favoriteQuestHeaderExpanded: FavoriteQuestHeaderExpanded
)

data class Quest(
    val title: String,
    val desc: String,
    val isCompleted: Boolean,
    val challengingCount: Int,
    val questStar: Int,
    val numOfComplete: Int
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
    val ongoingQuestFooter: OngoingQuestFooter,
    val ongoingQuestAuthClick: OngoingQuestAuthClick
)

data class OngoingQuestFooter(
    val id: Int = globalId.getAndIncrement()
)

data class FavoriteQuestHeader(
    val isExpanded: Boolean = false,
    val title: String,
    val desc: String,
    val isCompleted: Boolean,
    val numOfComplete: Int,
    val id: Int = globalId.getAndIncrement()
)

data class FavoriteQuest(
    val favoriteQuestHeader: FavoriteQuestHeader,
    val questConstraints: List<QuestConstraint>,
    val favoriteQuestFooter: FavoriteQuestFooter
)

data class FavoriteQuestFooter(
    val id: Int = globalId.getAndIncrement()
)

data class QuestConstraint(
    val constraintNum: Int,
    val content: String,
    val isCompleted: Boolean,
    val id: Int = globalId.getAndIncrement()
)