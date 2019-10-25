package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/*
@Dao
interface QuestConstraintDao{

    @Query("SELECT * FROM quest_constraint_table")
    fun getAllRepos(): List<QuestConstraint>

    @Query("SELECT * FROM quest_constraint_table INNER JOIN quest_table ON quest_table.quest_id = :id")
    fun getQuestConstraints(id: Int): LiveData<List<QuestConstraint>>
}
 */