package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReviewDao {

    @Insert
    fun insert(review: Review)

    @Query("SELECT * FROM quest_review_table")
    fun getAllReviews(): LiveData<List<Review>>

    @Query("DELETE FROM quest_review_table")
    fun deleteAll()

    @Query("SELECT * FROM quest_review_table WHERE id= :id")
    fun getReview(id: Int): LiveData<Review>

    @Query("SELECT quest_review_table.* FROM quest_table INNER JOIN quest_review_table ON quest_table.id = quest_review_table.quest_id AND quest_table.id = :id")
    fun getQuestReviewItem(id: Int) : LiveData<List<Review>>
}