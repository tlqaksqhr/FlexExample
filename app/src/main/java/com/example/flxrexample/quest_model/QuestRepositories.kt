package com.example.flxrexample.quest_model

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.flxrexample.app.DataQuestApplication

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

    fun addReview(review: Review) {
        InsertReviewAsyncTask(reviewDao).execute(review)
    }

    fun getStarAccount() = starAccountDao.getStarAccount()

    fun updateStarAccount(starAccount: StarAccount){
        UpdateStarAccountAsyncTask(starAccountDao).execute(starAccount)
    }

    fun insertStarAccount(starAccount: StarAccount){
        InsertStarAccountAsyncTask(starAccountDao).execute(starAccount)
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