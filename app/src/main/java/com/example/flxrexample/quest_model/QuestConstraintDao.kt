package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface QuestConstraintDao{

    @Query("SELECT * FROM quest_constraint_table")
    fun getAllRepos(): List<QuestConstraint>

    @Insert
    fun insert(questConstraint: QuestConstraint)

    @Insert
    fun insertAll(questConstraint: List<QuestConstraint>)

    @Query("SELECT COUNT(quest_constraint_table.id) FROM quest_constraint_table WHERE quest_id = :id")
    fun getQuestConstraintsCount(id: Int) : LiveData<Int>

    @Query("SELECT * FROM quest_constraint_table INNER JOIN quest_table ON quest_table.id = :id")
    fun getQuestConstraints(id: Int): LiveData<List<QuestConstraint>>
}