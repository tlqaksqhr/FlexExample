package com.example.flxrexample.quest

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.flxrexample.R
import com.example.flxrexample.databinding.QuestMainFragmentBinding
import com.example.flxrexample.quest_model.Quest
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

    private lateinit var binding: QuestMainFragmentBinding
    private lateinit var viewModel: QuestMainViewModel

    private lateinit var googleMap: GoogleMap
    private lateinit var questListAdapter: QuestListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.quest_main_fragment, container, false)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        this.googleMap = googleMap
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestMainViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.quest_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        questListAdapter = QuestListViewAdapter()

        quest_list_view.layoutManager = LinearLayoutManager(this.context)
        quest_list_view.adapter = questListAdapter
        quest_list_view.addItemDecoration(SpacingItemDecorator(1,20))

        viewModel.getQuests().observe(this, Observer<List<Quest>> { quests ->
            questListAdapter.submitList(quests)
            addMarker(quests)
        })
    }

    private fun addMarker(quests: List<Quest>) {
        quests.forEach{quest ->
            val markerOptions = MarkerOptions()
            //markerOptions.icon()
            googleMap.addMarker(markerOptions.position(quest.latLng).title("서울특별시"))
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quests.first().latLng, 12.toFloat()))
    }

}
