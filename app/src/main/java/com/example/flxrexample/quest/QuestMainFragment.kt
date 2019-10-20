package com.example.flxrexample.quest

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.flxrexample.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class QuestMainFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = QuestMainFragment()
    }

    private lateinit var viewModel: QuestMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //val mapFragment = childFragmentManager.findFragmentById(R.id.quest_map)
        //mapFragment.getMapAsync(this)

        return inflater.inflate(R.layout.quest_main_fragment, container, false)
    }

    override fun onMapReady(googlemap: GoogleMap) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
