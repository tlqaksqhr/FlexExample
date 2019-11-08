package com.example.flxrexample.quest_make

import android.location.Address
import com.example.flxrexample.quest_model.QuestConstraint
import com.google.android.gms.maps.model.Marker

interface QuestMakeEventListener {
    fun showMarkerDialog(address: Address, marker: Marker)
    fun addMarker(address: Address) : Marker
    fun addPicture(item: QuestConstraint)
}