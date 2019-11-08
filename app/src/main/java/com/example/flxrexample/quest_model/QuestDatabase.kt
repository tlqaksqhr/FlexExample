package com.example.flxrexample.quest_model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [(Quest::class),(QuestConstraint::class),(Review::class)], version = 1)
@TypeConverters(LatLngConverter::class, DateConverter::class)
abstract class QuestDatabase : RoomDatabase() {
    abstract fun questDao(): QuestDao
    abstract fun questConstraintDao(): QuestConstraintDao
    abstract fun reviewDao() : ReviewDao
}