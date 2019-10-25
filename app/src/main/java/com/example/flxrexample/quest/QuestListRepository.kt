package com.example.flxrexample.quest

import com.example.flxrexample.quest_model.Quest

object QuestListRepository {

    fun QuestListFactory() : MutableList<Quest> {
        return mutableListOf(
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false, 12345,3000,9989),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false, 52312,4500,9989),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false, 76445,1500,9989),
            Quest("제주로, 해녀로","바다속 풍경을 사진으로 찍고 인증하세요!",false, 42165,2000,9989)
        )
    }
}