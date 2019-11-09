package com.example.flxrexample.quest_model

import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestConstraint
import com.example.flxrexample.quest_model.Review
import com.google.android.gms.maps.model.LatLng
import java.util.*


object QuestListFactory {

    fun questListFactory() : MutableList<Quest>{
        return mutableListOf(
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733),
                "2019-10-20","2019-10-28"
            ),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733),
                "2019-10-20","2019-10-28"
            ),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733),
                "2019-10-20","2019-10-28"
            ),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733),
                "2019-10-20","2019-10-28"
            )
        )
    }

    fun questConstraintFactory(): List<QuestConstraint>{
        return listOf(
            QuestConstraint(10, "asdasdd", false,"",11),
            QuestConstraint(10, "asdasdd", false,"",11),
            QuestConstraint(10, "asdasdd", false,"",11),
            QuestConstraint(10, "asdasdd", false,"",11)
        )
    }

    fun questReviewFactory() : List<Review> {
        return listOf(
            Review("Review1",2.5.toFloat(),false),
            Review("Review2",3.5.toFloat(),false),
            Review("Review3",2.0.toFloat(),false),
            Review("Review4",1.5.toFloat(),false),
            Review("Review5",4.0.toFloat(),false)
        )
    }

    fun questAuthItemFactory(): List<QuestAuthImage> {
        return listOf(
            QuestAuthImage(""),
            QuestAuthImage(""),
            QuestAuthImage("")
        )
    }

    fun questDummyAuthItemFactory(n: Int) : List<QuestAuthImage> {
        var dummyList = mutableListOf<QuestAuthImage>()

        for(i in 1..n) {
            dummyList.add(QuestAuthImage(""))
        }
        return dummyList.toList()
    }
}