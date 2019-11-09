package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface QuestAuthImageDao {

    @Query("SELECT * FROM quest_auth_image_table")
    fun getAllAuthImages(): List<QuestAuthImage>

    @Insert
    fun insert(questAuthImage: QuestAuthImage)

    @Insert
    fun insertAll(questAuthImage: List<QuestAuthImage>)

    @Query("SELECT * FROM quest_auth_image_table INNER JOIN quest_table, quest_constraint_table ON quest_constraint_table.id = quest_auth_image_table.quest_constraint_id AND quest_constraint_table.quest_id = quest_table.id AND quest_table.id = :id")
    fun getQuestAuthImages(id: Int): LiveData<List<QuestAuthImage>>
}