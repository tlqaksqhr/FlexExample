package com.example.flxrexample.quest

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.quest_main_fragment.*
import kotlinx.android.synthetic.main.quest_ongoing_fragment.*
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor



class QuestMainFragment : Fragment(), OnMapReadyCallback,
    QuestMainEventListener{

    companion object {
        fun newInstance() = QuestMainFragment()
    }

    private lateinit var binding: QuestMainFragmentBinding
    private lateinit var viewModel: QuestMainViewModel

    private lateinit var googleMap: GoogleMap
    private lateinit var questListAdapter: QuestListViewAdapter

    private lateinit var questInfoWindowAdapter: QuestInfoWindowAdapter
    private lateinit var quest: Quest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.quest_main_fragment, container, false)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        this.googleMap = googleMap
        this.questInfoWindowAdapter = QuestInfoWindowAdapter(activity?.applicationContext!!)
        this.googleMap.setInfoWindowAdapter(this.questInfoWindowAdapter)

        this.googleMap.setOnInfoWindowClickListener {
            transitToQuestView(quest.id)
        }

        this.googleMap.setOnInfoWindowLongClickListener {
            transitToQuestView(quest.id)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestMainViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.quest_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        questListAdapter = QuestListViewAdapter(this)

        quest_list_view.layoutManager = LinearLayoutManager(this.context)
        quest_list_view.adapter = questListAdapter
        quest_list_view.addItemDecoration(SpacingItemDecorator(1,20))

        viewModel.getQuests().observe(this, Observer<List<Quest>> { quests ->
            questListAdapter.submitList(quests)

            if(!quests.isEmpty())
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quests.last().latLng, 20.toFloat()))
        })

    }
    override fun transitToQuestView(id: Int){
        val intent = Intent(this.context, QuestViewActivity::class.java)
        intent.putExtra("id",id)
        this.context?.startActivity(intent)
    }

    override fun showMarkerDialog(quest: Quest, marker: Marker){
        questInfoWindowAdapter.bind(quest)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quest.latLng, 20.toFloat()))
        marker.showInfoWindow()
        this.quest = quest
    }

    override fun addMarker(quest: Quest) : Marker{
        val markerOptions = MarkerOptions()
        markerOptions.icon(bitmapDescriptorFromVector(this.context!!, R.drawable.ic_map_off))
        val marker = googleMap.addMarker(markerOptions.position(quest.latLng))
        return marker
    }


    private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {

        var drawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }

        val bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth!!,
            drawable?.intrinsicHeight!!,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
