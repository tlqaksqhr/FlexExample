package com.example.flxrexample.app

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestDao
import com.example.flxrexample.quest_model.QuestDatabase
import com.google.android.gms.maps.model.LatLng

class DataQuestApplication : Application(){
    companion object {
        lateinit var database: QuestDatabase
        private lateinit var instance: DataQuestApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        database = Room.databaseBuilder(this, QuestDatabase::class.java, "quest_database")
            .addCallback(roomDatabaseCallback).build()
    }

    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase){
            super.onOpen(db)
            PopulateDbAsync(database).execute()
        }
    }

    private class PopulateDbAsync(db: QuestDatabase) : AsyncTask<Void, Void, Void>(){
        private val questDao: QuestDao = db.questDao()

        override fun doInBackground(vararg params: Void): Void? {

            val quest = Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733))
            questDao.insert(quest)
            questDao.insert(Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                52312,4500,9989, "삼도일동 535-3번지 지하 제주시 제주특별자치도 KR", LatLng(33.252688, 126.623467)))
            questDao.insert(Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                76445,1500,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.510583, 126.491332)))
            questDao.insert(Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                42165,2000,9989, "삼도일동 535-3번지 지하 제주시 제주특별자치도 KR", LatLng(33.501074, 126.520030)))

            return null
        }
    }
}