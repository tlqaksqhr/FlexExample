package com.example.flxrexample.quest

import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestConstraint
import com.google.android.gms.maps.model.LatLng


object QuestListFactory {

    fun questListFactory() : MutableList<Quest>{
        return mutableListOf(
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733)
            ),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733)
            ),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733)
            ),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false,
                12345,3000,9989, "제주특별자치도 제주시 공항로 2", LatLng(33.253550, 126.564733)
            )
        )
    }

    fun questConstraintFactory(): List<QuestConstraint>{
        return listOf(
            QuestConstraint(10, "asdasdd", false,11),
            QuestConstraint(10, "asdasdd", false,11),
            QuestConstraint(10, "asdasdd", false,11),
            QuestConstraint(10, "asdasdd", false,11)
        )
    }
}