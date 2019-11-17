package com.example.flxrexample.quest_make

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.MainActivity

import com.example.flxrexample.R
import com.example.flxrexample.quest.QuestMainFragment
import com.example.flxrexample.quest_model.Quest
import com.example.flxrexample.quest_model.QuestConstraint
import com.example.flxrexample.quest_model.QuestViewItem
import com.example.flxrexample.quest_ongoing.QuestOngoingAuthActivity
import com.example.flxrexample.quest_ongoing.SpacingItemDecoratorBottom
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.quest_make_fragment.*
import java.io.File
import java.io.IOException
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

class QuestMakeFragment : Fragment(), OnMapReadyCallback, QuestMakeEventListener{

    companion object {
        fun newInstance() = QuestMakeFragment()
    }

    val IMAGE_CAPTURE_CODE = 1
    val REQUEST_IMAGE_CAPTURE = 1
    private val PERMISSION_REQUEST_CODE: Int = 101

    private lateinit var viewModel: QuestMakeViewModel

    private lateinit var googleMap: GoogleMap
    private lateinit var geoCoder: Geocoder

    private lateinit var locationSearchListViewAdapter : LocationSearchListViewAdapter
    private lateinit var questConstraintUploadViewAdapter: QuestConstraintUploadViewAdapter

    private var mCurrentPhotoPath: String? = null
    private lateinit var questConstraintItem: QuestConstraint

    private lateinit var mapPos : LatLng
    private lateinit var strAddr: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quest_make_fragment, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.geoCoder = Geocoder(context?.applicationContext, Locale.KOREAN)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestMakeViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.quest_make_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        quest_make_start_date.setOnClickListener {
            val dpd = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                quest_make_start_date.text = """$year/${monthOfYear + 1}/$dayOfMonth"""

            }, 2019, 10, 27)
            dpd.show()
        }

        quest_make_end_date.setOnClickListener {
            val dpd = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                quest_make_end_date.text = """$year/${monthOfYear + 1}/$dayOfMonth"""

            }, 2019, 10, 27)
            dpd.show()
        }

        locationSearchListViewAdapter =
            LocationSearchListViewAdapter(this)

        questConstraintUploadViewAdapter =
            QuestConstraintUploadViewAdapter(this)

        quest_make_location_searchlist.layoutManager = LinearLayoutManager(this.context)
        quest_make_location_searchlist.adapter = locationSearchListViewAdapter

        quest_make_condition_list.layoutManager = LinearLayoutManager(this.context)
        quest_make_condition_list.adapter = questConstraintUploadViewAdapter
        quest_make_condition_list.addItemDecoration(SpacingItemDecoratorBottom(1,20))

        quest_make_location_searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                quest_make_location_searchlist.visibility = View.VISIBLE
                locationSearchListViewAdapter.submitList(queryAddr(query))
                return true
            }
        })

        viewModel.getAllQuestConstraint().observe(this, Observer<List<QuestConstraint>> { constraints ->
            questConstraintUploadViewAdapter.submitList(constraints.toList())
        })

        viewModel.getImageURL().observe(this, Observer<String> {url ->
            viewModel.addQuestConstraintImageURL(questConstraintItem, url)
        })

        quest_make_condition_add_btn.setOnClickListener {
            viewModel.addQuestConstraint(QuestConstraint(
                0,
                "",
                false,
                ""
            ))
        }

        quest_make_condition_remove_btn.setOnClickListener {
            viewModel.removeLastQuestConstraint()
        }

        quest_make_register_btn.setOnClickListener {
            val contents = questConstraintUploadViewAdapter.getContent()
            contents.forEach { entry ->
                viewModel.addQuestConstraintContent(QuestConstraint(
                    0,
                    "",
                    false,
                    ""
                ),entry)
            }

            val totalStar = quest_make_reward_star_person_total_text.text.toString()
            val perStar = quest_make_reward_star_count_text.text.toString()

            if(totalStar.isDigitsOnly() && perStar.isDigitsOnly()){
                val quest = Quest(
                    quest_make_title.text.toString(),
                    quest_make_desc.text.toString(),
                    false,
                    0,
                    parseInt(totalStar),
                    parseInt(perStar),
                    0,
                    strAddr,
                    mapPos,
                    quest_make_start_date.text.toString(),
                    quest_make_end_date.text.toString(),
                    false,
                    false)

                viewModel.addQuest(quest)

                val transaction = this.fragmentManager?.beginTransaction()
                transaction?.replace(R.id.main_container,QuestMainFragment())
                transaction?.commit()
            }
        }
    }

    private fun queryAddr(query: String) : List<Address>{
        try {
            return geoCoder.getFromLocationName(query, 10)
        }catch(e: IOException) {
            Toast.makeText(this.context,"주소 검색 실패",Toast.LENGTH_SHORT).show()
        }
        return listOf()
    }


    override fun showMarkerDialog(addr: Address, marker: Marker){
        quest_make_location_searchlist.visibility = View.GONE
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(addr.latitude,addr.longitude), 12.toFloat()))
        marker.showInfoWindow()
    }

    override fun addMarker(addr: Address) : Marker {

        val mapPos = LatLng(addr.latitude,addr.longitude)
        strAddr = addr.getAddressLine(0)

        val markerOptions = MarkerOptions()
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))
        val marker = googleMap.addMarker(markerOptions
            .position(mapPos)
            .title(addr.featureName)
            .snippet(addr.getAddressLine(0)))

        this.mapPos = mapPos

        return marker
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            //To get the File for further usage
            val auxFile = File(mCurrentPhotoPath)
            viewModel.setImageURL(auxFile.absolutePath)
        }else if(requestCode == RESULT_CANCELED){
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    takePicture()

                } else {
                    Toast.makeText(this.context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }

    override fun addPicture(item: QuestConstraint) {
        if (checkPersmission()) {
            takePicture()
        }
        else
            requestPermission()

        questConstraintItem = item
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this.activity!!, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.activity!!,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this.activity!!, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), PERMISSION_REQUEST_CODE)
    }


    private fun takePicture() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()

        val uri: Uri = FileProvider.getUriForFile(
            this.activity!!,
            "com.example.flxrexample.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

    @Throws(IOException::class)
    private fun createFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            mCurrentPhotoPath = absolutePath
        }
    }

}
