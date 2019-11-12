package com.example.flxrexample.quest_model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StarAccountDao {

    @Insert
    fun insert(starAccount: StarAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(starAccount: StarAccount)

    @Query("SELECT * FROM star_account_table")
    fun getStarAccount() : LiveData<StarAccount>
}