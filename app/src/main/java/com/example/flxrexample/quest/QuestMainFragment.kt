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
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.PopupWindow
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import kotlinx.android.synthetic.main.marker_dialog.view.*


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

    private lateinit var popWindow: PopupWindow
    private lateinit var boxSize: Point

    private var isStarted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.quest_main_fragment, container, false)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        isStarted = true

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

        boxSize = Point(mapFragment.view?.measuredWidth!!,mapFragment.view?.measuredHeight!!)

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

    override fun favoriteBtnClickEvent(quest: Quest){
        var isFavorite = quest.isFavorite

        if(!quest.isOngoing){
            isFavorite = true
        }else{
            Toast.makeText(this.context,"찜 목록에 추가할 수 없습니다.",Toast.LENGTH_SHORT)
        }

        viewModel.updateQuest(Quest(
            quest.title,
            quest.desc,
            quest.isCompleted,
            quest.challengingCount,
            quest.totalStar,
            quest.questStar,
            quest.numOfComplete,
            quest.address,
            quest.latLng,
            quest.startDate,
            quest.endDate,
            isFavorite,
            quest.isOngoing,
            quest.id
        ))
    }

    override fun transitToQuestView(id: Int){
        val intent = Intent(this.context, QuestViewActivity::class.java)
        intent.putExtra("id",id)
        this.context?.startActivity(intent)
    }

    override fun showMarkerDialog(quest: Quest, marker: Marker){

        if(this::popWindow.isInitialized){
            popWindow.dismiss()
        }

        questInfoWindowAdapter.bind(quest)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quest.latLng, 20.toFloat()))

        val projection = googleMap.projection
        val screenPosition = projection.toScreenLocation(marker.position)

        showFilterPopup(screenPosition.x,screenPosition.y)

        //marker.setInfoWindowAnchor(0.5.toFloat(), 2.8.toFloat())
        //marker.showInfoWindow()
        this.quest = quest
    }

    private fun showFilterPopup(x: Int, y: Int){

        val layoutInflater : LayoutInflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val inflatedView = layoutInflater.inflate(R.layout.marker_dialog,null,false)

        val display = activity!!.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        var metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        val logicalDensity = metrics.density

        val width = Math.ceil(320.toDouble() * logicalDensity).toInt()
        val height = Math.ceil(160.toDouble() * logicalDensity).toInt()

        popWindow = PopupWindow(inflatedView,width,height)

        popWindow.isOutsideTouchable = true
        popWindow.setBackgroundDrawable(ContextCompat.getDrawable(activity!!.window.decorView.context,R.drawable.popup_background))

        //(30*logicalDensity).toInt()
        popWindow.showAtLocation(activity!!.window.decorView, Gravity.TOP,-(4*logicalDensity).toInt(),y+(82*logicalDensity).toInt())

        popWindow.contentView.marker_dialog_btn.setOnClickListener {
            transitToQuestView(quest.id)
        }
    }

    override fun addMarker(quest: Quest, isMark: Boolean) : Marker{
        val markerOptions = MarkerOptions()
        if(isMark){
            val bitmapDraw = resources.getDrawable(R.drawable.marker_icon) as BitmapDrawable
            val b = bitmapDraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, 100, 160,false)
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        }else {
            markerOptions.icon(bitmapDescriptorFromVector(this.context!!, R.drawable.ic_map_off))
        }

        val marker = googleMap.addMarker(markerOptions.position(quest.latLng))
        return marker
    }

    override fun removeMarker() {
        googleMap.clear()
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
