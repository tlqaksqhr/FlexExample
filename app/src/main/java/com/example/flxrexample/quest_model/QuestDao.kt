package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestDao {

    @Insert
    fun insert(quest: Quest)

    @Query("SELECT * FROM quest_table")
    fun getAllQuests(): LiveData<List<Quest>>

    @Query("SELECT * FROM quest_table WHERE quest_id = :id")
    fun getQuest(id: Int): LiveData<Quest>

    //@Query("SELECT * FROM quest_table, quest_constraint_table WHERE quest_table.quest_id = :id")
    //fun getQuestViewItem(id: Int) : LiveData<QuestViewItem>
}