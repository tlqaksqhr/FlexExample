package com.example.flxrexample.quest_model

import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.flxrexample.app.DataQuestApplication
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class QuestRepository{
    private val questDao: QuestDao = DataQuestApplication.database.questDao()
    private val questConstraintDao: QuestConstraintDao = DataQuestApplication.database.questConstraintDao()
    private val reviewDao: ReviewDao = DataQuestApplication.database.reviewDao()
    private val questAuthImageDao: QuestAuthImageDao = DataQuestApplication.database.questAuthImageDao()
    private val starAccountDao: StarAccountDao = DataQuestApplication.database.starAccountDao()

    private val allQuests: LiveData<List<Quest>>

    init{
        allQuests = questDao.getAllQuests()
    }

    fun getQuests(): LiveData<List<Quest>> = allQuests

    fun getQuest(id: Int) = questDao.getQuest(id)

    fun getQuestViewItem(id: Int) = questDao.getQuestViewItem(id)

    fun getQuestReviews(id: Int) = reviewDao.getQuestReviewItem(id)

    fun getRecentQuestID() = questDao.getRecentQuestID()

    fun getQuestConstraintsCount(id: Int) = questConstraintDao.getQuestConstraintsCount(id)

    fun getQuestConstraints(id: Int) = questConstraintDao.getQuestConstraints(id)

    fun addQuestViewItem(questViewItem: QuestViewItem){
        AsyncTask.execute {
            InsertQuestAsyncTask(questDao).execute(questViewItem.quest)
            val id = getRecentQuestID() + 1
            InsertQuestConstraintsAsyncTask(questConstraintDao, id).execute(questViewItem.questConstraints)
        }
    }

    fun getQuestAuthImages(id: Int) = questAuthImageDao.getQuestAuthImages(id)

    fun addQuestAuthImages(questAuthImages: List<QuestAuthImage>) {
        InsertQuestAuthImagesAsyncTask(questAuthImageDao).execute(questAuthImages)
    }

    fun updateQuestConstraint(questConstraint: QuestConstraint) {
        UpdateQuestConstraintsAsyncTask(questConstraintDao).execute(questConstraint)
    }

    fun addReview(review: Review) {
        InsertReviewAsyncTask(reviewDao).execute(review)
    }

    fun getStarAccount() = starAccountDao.getStarAccount()

    fun updateStarAccount(starAccount: StarAccount){
        UpdateStarAccountAsyncTask(starAccountDao).execute(starAccount)
    }

    fun updateQuest(quest: Quest){
        UpdateQuestAsyncTask(questDao).execute(quest)
    }

    fun insertStarAccount(starAccount: StarAccount){
        InsertStarAccountAsyncTask(starAccountDao).execute(starAccount)
    }

    fun getAllQuestConstraints() = questConstraintDao.getAllQuestConstraints()


    private fun uploadToServer(questAuthImage: QuestAuthImage, path: String){
        val retrofit = DataQuestApplication.mRetrofit
        val uploadAPIs = retrofit.create(RemoteRepository::class.java)

        val file = File(path)

        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("userfile", file.name, fileReqBody)

        val call = uploadAPIs.uploadImage(part)

        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()

                if(result != null) {
                    Log.d("Image Response", "Message : " + result.toString())
                    Handler().postDelayed({
                        getImageAuthResult(questAuthImage,result.toString())
                    },1000*100)
                }
            }
        })
    }

    private fun getImageAuthResult(questAuthImage: QuestAuthImage, filePath: String) {
        val retrofit = DataQuestApplication.mRetrofit
        val imageAuthAPIs = retrofit.create(RemoteRepository::class.java)

        val call = imageAuthAPIs.getAuthResult(ImageAuthData(filePath))

        call.enqueue(object : Callback<ImageAuthResult> {
            override fun onFailure(call: Call<ImageAuthResult>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ImageAuthResult>,
                response: Response<ImageAuthResult>
            ) {
                val result = response.body()
                var predictResult = false

                if(result != null && result.predicts == "True"){
                    predictResult = true
                }
            }
        })
    }
    

    private class UpdateQuestAsyncTask internal constructor(private val questDao: QuestDao) : AsyncTask<Quest, Void, Void>() {
        override fun doInBackground(vararg params: Quest): Void? {
            questDao.update(params[0])
            return null
        }
    }


    private class UpdateQuestConstraintsAsyncTask internal constructor(private val questConstraintDao: QuestConstraintDao) : AsyncTask<QuestConstraint, Void, Void>() {
        override fun doInBackground(vararg params: QuestConstraint): Void? {
            questConstraintDao.update(params[0])
            return null
        }
    }

    private class UpdateStarAccountAsyncTask internal constructor(private val starAccountDao: StarAccountDao) : AsyncTask<StarAccount, Void, Void>() {
        override fun doInBackground(vararg params: StarAccount): Void? {
            starAccountDao.update(params[0])
            return null
        }
    }

    private class InsertStarAccountAsyncTask internal constructor(private val starAccountDao: StarAccountDao) : AsyncTask<StarAccount, Void, Void>() {
        override fun doInBackground(vararg params: StarAccount): Void? {
            starAccountDao.insert(params[0])
            return null
        }
    }


    private class InsertReviewAsyncTask internal constructor(private val reviewDao: ReviewDao) : AsyncTask<Review, Void, Void>() {
        override fun doInBackground(vararg params: Review): Void? {
            reviewDao.insert(params[0])
            return null
        }
    }

    private class InsertQuestAuthImagesAsyncTask internal constructor(private val questAuthImageDao: QuestAuthImageDao) : AsyncTask<List<QuestAuthImage>, Void, Void>() {
        override fun doInBackground(vararg params: List<QuestAuthImage>): Void? {
            questAuthImageDao.insertAll(params[0])
            return null
        }
    }


    private class InsertQuestAuthImageAsyncTask internal constructor(private val questAuthImageDao: QuestAuthImageDao) : AsyncTask<QuestAuthImage, Void, Void>() {
        override fun doInBackground(vararg params: QuestAuthImage): Void? {
            questAuthImageDao.insert(params[0])
            return null
        }
    }

    private class InsertQuestAsyncTask internal constructor(private val questDao: QuestDao) : AsyncTask<Quest, Void, Void>() {
        override fun doInBackground(vararg params: Quest): Void? {
            questDao.insert(params[0])
            return null
        }
    }

    private class InsertQuestConstraintsAsyncTask internal constructor(private val questConstraintDao: QuestConstraintDao, private val id: Int) : AsyncTask<List<QuestConstraint>, Void, Void>() {
        override fun doInBackground(vararg params: List<QuestConstraint>): Void? {
            val questId = id
            val newConstraints = ArrayList<QuestConstraint>()
            params[0].forEach {
                val item = QuestConstraint(
                    it.constraintNum,
                    it.content,
                    it.isCompleted,
                    it.pictureURL,
                    questId
                )
                newConstraints.add(item)
            }
            questConstraintDao.insertAll(newConstraints.toList())
            return null
        }
    }
}