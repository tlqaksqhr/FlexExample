package com.example.flxrexample.quest_model

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.flxrexample.app.DataQuestApplication

class QuestRepository{
    private val questDao: QuestDao = DataQuestApplication.database.questDao()
    private val questConstraintDao: QuestConstraintDao = DataQuestApplication.database.questConstraintDao()
    private val reviewDao: ReviewDao = DataQuestApplication.database.reviewDao()

    private val allQuests: LiveData<List<Quest>>
    init{
        allQuests = questDao.getAllQuests()
    }

    fun getQuests(): LiveData<List<Quest>> = allQuests

    fun getQuest(id: Int) = questDao.getQuest(id)

    fun getQuestViewItem(id: Int) = questDao.getQuestViewItem(id)

    fun getQuestReviews(id: Int) = reviewDao.getQuestReviewItem(id)

    fun getRecentQuestID() = questDao.getRecentQuestID()

    fun addQuestViewItem(questViewItem: QuestViewItem){
        AsyncTask.execute {
            InsertQuestAsyncTask(questDao).execute(questViewItem.quest)
            val id = getRecentQuestID() + 1
            InsertQuestConstraintsAsyncTask(questConstraintDao, id).execute(questViewItem.questConstraints)
        }
    }

    fun addReview(review: Review) {
        InsertReviewAsyncTask(reviewDao).execute(review)
    }

    private class InsertReviewAsyncTask internal constructor(private val reviewDao: ReviewDao) : AsyncTask<Review, Void, Void>() {
        override fun doInBackground(vararg params: Review): Void? {
            reviewDao.insert(params[0])
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