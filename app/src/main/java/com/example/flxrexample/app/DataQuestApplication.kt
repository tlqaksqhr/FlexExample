package com.example.flxrexample.app

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amitshekhar.DebugDB
import com.example.flxrexample.quest_model.*
import com.google.android.gms.maps.model.LatLng
import java.util.*
import retrofit2.Retrofit
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.flxrexample.R
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory



class DataQuestApplication : Application(){
    companion object {
        lateinit var database: QuestDatabase
        lateinit var mRetrofit: Retrofit
        private lateinit var instance: DataQuestApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        val stethoInterceptingClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        mRetrofit = Retrofit.Builder().baseUrl(getString(R.string.baseUrl)).addConverterFactory(
            GsonConverterFactory.create()).client(stethoInterceptingClient).build()

        database = Room.inMemoryDatabaseBuilder(this, QuestDatabase::class.java)
            .addCallback(roomDatabaseCallback).build()

        //database = Room.databaseBuilder(this, QuestDatabase::class.java, "quest_database")
        //    .addCallback(roomDatabaseCallback).build()
    }

    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase){
            super.onOpen(db)
            PopulateDbAsync(database).execute()
        }
    }

    private class PopulateDbAsync(db: QuestDatabase) : AsyncTask<Void, Void, Void>(){
        private val questDao: QuestDao = db.questDao()
        private val reviewDao: ReviewDao = db.reviewDao()
        private val questConstraintDao: QuestConstraintDao = db.questConstraintDao()
        private val starAccountDao: StarAccountDao = db.starAccountDao()

        override fun doInBackground(vararg params: Void): Void? {

            questDao.deleteAll()

            starAccountDao.insert(StarAccount(1,4000))

            val quest = Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,10000,100,0, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733),
                "2019-10-20","2019-10-28",false,false,1)
            questDao.insert(quest)

            var constraint = QuestConstraint(0,"바다속 풍경을 사진으로 찍고 인증하세요!",false,
                "https://d3h30waly5w5yx.cloudfront.net/images/tour/pictures/jeju-discover-scuba-diving3.jpg",1)

            questConstraintDao.insert(constraint)

            constraint = QuestConstraint(1,"호롷롷로홀홀홀홀호롷롷ㄹ",false,
                "https://d3h30waly5w5yx.cloudfront.net/images/tour/pictures/jeju-discover-scuba-diving3.jpg",1)

            constraint = QuestConstraint(2,"호롷롷로홀홀홀홀호롷롷ㄹ",true,
                "https://d3h30waly5w5yx.cloudfront.net/images/tour/pictures/jeju-discover-scuba-diving3.jpg",1)

            questConstraintDao.insert(constraint)

            reviewDao.insert(Review("리뷰1",3.5.toFloat(),false,1))
            reviewDao.insert(Review("리뷰2",1.0.toFloat(),false,1))
            reviewDao.insert(Review("리뷰3",4.5.toFloat(),false,1))
            reviewDao.insert(Review("리뷰4",2.0.toFloat(),false,1))

            val quest2 = Quest("제주로!","rororororororor!",false,
                12345,10000,100, 0,"제주특별자치도 제주시 공항로 3", LatLng(32.253550, 123.564733),
                "2019-10-20","2019-10-28",false,false,2)
            questDao.insert(quest2)

            var constraint2 = QuestConstraint(10,"zkzkzkzkzkzkzkzzk!",false,
                "https://d3h30waly5w5yx.cloudfront.net/images/tour/pictures/jeju-discover-scuba-diving3.jpg",2)

            questConstraintDao.insert(constraint2)

            constraint2 = QuestConstraint(20,"gkgkgkgkgkgkgkgk",false,
                "https://d3h30waly5w5yx.cloudfront.net/images/tour/pictures/jeju-discover-scuba-diving3.jpg",2)

            questConstraintDao.insert(constraint2)

            reviewDao.insert(Review("리뷰6",3.5.toFloat(),false,2))
            reviewDao.insert(Review("리뷰7",1.0.toFloat(),false,2))
            reviewDao.insert(Review("리뷰8",4.5.toFloat(),false,2))
            reviewDao.insert(Review("리뷰9",2.0.toFloat(),false,2))

            return null
        }
    }
}