package com.example.flxrexample.quest

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.flxrexample.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.quest_main_fragment.*
import kotlinx.android.synthetic.main.quest_ongoing_fragment.*

class QuestMainFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = QuestMainFragment()
    }

    private lateinit var viewModel: QuestMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.quest_main_fragment, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val seoul = LatLng(37.5665, 126.9780)
        googleMap.addMarker(MarkerOptions().position(seoul).title("서울특별시"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 10.toFloat()))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestMainViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.quest_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        quest_list_view.layoutManager = LinearLayoutManager(this.context)
        quest_list_view.adapter = QuestListViewAdapter(QuestListRepository.QuestListFactory())
        quest_list_view.addItemDecoration(SpacingItemDecorator(1,20))

    }

}
