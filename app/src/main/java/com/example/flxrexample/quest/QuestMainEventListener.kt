package com.example.flxrexample.quest

import com.example.flxrexample.quest_model.Quest
import com.google.android.gms.maps.model.Marker

interface QuestMainEventListener {
    fun showMarkerDialog(quest: Quest, marker: Marker)
    fun transitToQuestView(id: Int)
    fun addMarker(quest: Quest, isMark: Boolean) : Marker
    fun removeMarker()
    fun favoriteBtnClickEvent(quest: Quest)
}