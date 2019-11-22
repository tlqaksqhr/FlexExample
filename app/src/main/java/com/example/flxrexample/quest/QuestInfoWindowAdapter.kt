package com.example.flxrexample.quest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.LayoutInflaterCompat
import com.example.flxrexample.R
import com.example.flxrexample.quest_model.Quest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.marker_dialog.view.*

class QuestInfoWindowAdapter(val context: Context) : InfoWindowAdapter {
    lateinit var quest: Quest

    fun bind(quest: Quest){
        this.quest = quest
    }

    override fun getInfoContents(marker: Marker): View {

        val v : View = LayoutInflater.from(context).inflate(R.layout.marker_dialog, null)

        v.marker_dialog_title.text = quest.title
        v.marker_dialog_content.text = quest.desc
        v.marker_dialog_addr.text = quest.address

        return v
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}