package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestDao {

    @Insert
    fun insert(quest: Quest)

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Update
    fun update(quest: Quest)

    @Query("SELECT * FROM quest_table")
    fun getAllQuests(): LiveData<List<Quest>>

    @Query("DELETE FROM quest_table")
    fun deleteAll()

    @Query("SELECT * FROM quest_table WHERE id= :id")
    fun getQuest(id: Int): LiveData<Quest>

    @Query("SELECT id FROM quest_table ORDER BY id DESC LIMIT 1")
    fun getRecentQuestID() : Int

    //@Query("SELECT quest_table.*, quest_constraint_table.* FROM quest_table JOIN quest_constraint_table ON quest_table.id = quest_constraint_table.quest_id AND quest_table.id = :id")

    @Query("SELECT quest_table.title, quest_table.`desc`, quest_table.isCompleted, quest_table.challengingCount, quest_table.totalStar ,quest_table.questStar, quest_table.numOfComplete, quest_table.address, quest_table.latLng, quest_table.startDate, quest_table.endDate, quest_table.isFavorite, quest_table.isOngoing, quest_table.id, quest_constraint_table.constraintNum, quest_constraint_table.content, quest_constraint_table.pictureURL FROM quest_table JOIN quest_constraint_table ON quest_table.id = quest_constraint_table.quest_id AND quest_table.id = :id")
    fun getQuestViewItem(id: Int) : LiveData<QuestViewItem>
}