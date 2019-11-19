package com.example.flxrexample.quest_model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
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
    val totalStar: Int,
    val questStar: Int,
    val numOfComplete: Int,
    val address: String,
    val latLng: LatLng,
    val startDate: String,
    val endDate: String,
    val isFavorite: Boolean = false,
    val isOngoing: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


@Entity(tableName="quest_constraint_table", foreignKeys = [
    (ForeignKey(entity = Quest::class,
        parentColumns = ["id"],
        childColumns = ["quest_id"],
        onDelete = CASCADE))])
data class QuestConstraint(
    @ColumnInfo(name = "constraintNum")
    val constraintNum: Int,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "pictureURL")
    val pictureURL: String,
    @ColumnInfo(name = "quest_id")
    val questID: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = globalId.getAndIncrement()
)

data class QuestViewItem(
    @Embedded
    val quest: Quest,
    @Relation(parentColumn =  "id", entityColumn = "quest_id")
    val questConstraints: List<QuestConstraint>,
    @Relation(parentColumn =  "id", entityColumn = "quest_id")
    val questReviews: List<Review>
)

@Entity(tableName="quest_review_table", foreignKeys = [
    (ForeignKey(entity = Quest::class,
        parentColumns = ["id"],
        childColumns = ["quest_id"],
        onDelete = CASCADE))])
data class Review(
    val content: String,
    val rating: Float,
    val isRetry: Boolean,
    @ColumnInfo(name = "quest_id")
    val questID: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = globalId.getAndIncrement()
)

@Entity(tableName="quest_auth_image_table", foreignKeys = [
    (ForeignKey(entity = QuestConstraint::class,
        parentColumns = ["id"],
        childColumns = ["quest_constraint_id"],
        onDelete = CASCADE))])
data class QuestAuthImage(
    val pictureURL: String,
    @ColumnInfo(name = "quest_constraint_id")
    val questID: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = globalId.getAndIncrement()
)

@Entity(tableName="star_account_table")
data class StarAccount(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val starAmount: Int,
    val userID: Int = 0
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

data class ImageAuthData(
    @SerializedName("filename") val filename: String
)

data class ImageAuthResult(
    @SerializedName("filename") val filename: String,
    @SerializedName("predicts") val predicts: String
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