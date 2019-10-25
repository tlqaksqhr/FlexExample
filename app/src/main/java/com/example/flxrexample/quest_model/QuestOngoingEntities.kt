package com.example.flxrexample.quest_model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.android.gms.maps.model.LatLng
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

private val globalId = AtomicInteger(1)

typealias OngoingQuestHeaderExpanded = (quest: OngoingQuestHeader) -> Unit
typealias FavoriteQuestHeaderExpanded = (favorite: FavoriteQuestHeader) -> Unit
typealias OngoingQuestAuthClick = () -> Unit

@Entity(tableName="quest_table")
data class Quest(
    val title: String,
    val desc: String,
    val isCompleted: Boolean,
    val challengingCount: Int,
    val questStar: Int,
    val numOfComplete: Int,
    val address: String,
    val latLng: LatLng,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quest_id")
    val id: Int = 0
)


/*
@Entity(tableName="quest_constraint_table", foreignKeys = [
    (ForeignKey(entity = QuestConstraint::class,
        parentColumns = ["quest_id"],
        childColumns = ["quest_constraint_id"],
        onDelete = CASCADE))])*/
data class QuestConstraint(
    val constraintNum: Int,
    val content: String,
    val isCompleted: Boolean,
    //@PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "quest_constraint_id")
    val id: Int = globalId.getAndIncrement()
)


data class QuestViewItem(
    @Embedded
    val quest: Quest,
    @Embedded
    val questConstraints: List<QuestConstraint>
)


data class Container(
    val ongoingQuests: List<OngoingQuest>,
    val ongoingQuestHeaderExpanded: OngoingQuestHeaderExpanded,
    val favoriteQuests: List<FavoriteQuest>,
    val favoriteQuestHeaderExpanded: FavoriteQuestHeaderExpanded
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

/*

@Entity(tableName="quest_location_table", foreignKeys = [
    (ForeignKey(entity = QuestLocation::class,
        parentColumns = ["quest_id"],
        childColumns = ["quest_location_id"],
        onDelete = CASCADE))
])

data class QuestLocation(
    val country: String,
    val city: String,
    val address: String,
    val latLng: LatLng,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quest_location_id")
    val id: Int = 0
)

 */