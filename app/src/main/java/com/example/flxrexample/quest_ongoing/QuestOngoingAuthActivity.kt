package com.example.flxrexample.quest_ongoing

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityQuestOngoingAuthBinding
import com.example.flxrexample.quest_model.QuestAuthImage
import com.example.flxrexample.quest_model.QuestListFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class QuestOngoingAuthActivity : AppCompatActivity() {

    val IMAGE_CAPTURE_CODE = 1
    val REQUEST_IMAGE_CAPTURE = 1
    private val PERMISSION_REQUEST_CODE: Int = 101

    private lateinit var binding: ActivityQuestOngoingAuthBinding
    private lateinit var viewModel: QuestOngoingAuthViewModel

    private lateinit var questAuthImageListViewAdapter : QuestThumbListViewAdapter

    private var mCurrentPhotoPath: String? = null
    private var questID: Int = 0
    private lateinit var questAuthImageItem: QuestAuthImage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(QuestOngoingAuthViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityQuestOngoingAuthBinding>(this,
            R.layout.activity_quest_ongoing_auth)

        questAuthImageListViewAdapter = QuestThumbListViewAdapter()

        binding.questOngoingAuthThumbnailList.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        binding.questOngoingAuthThumbnailList.adapter = questAuthImageListViewAdapter
        binding.questOngoingAuthThumbnailList.addItemDecoration(SpacingItemDecoratorBottom(3,40))
        binding.questOngoingAuthThumbnailList.setHasFixedSize(true)

        binding.questOngoingAuthSubmitBtn.setOnClickListener {
            val intent = Intent(this,QuestOngoingReviewActivity::class.java)
            intent.putExtra("id",questID)
            startActivity(intent)
        }

        questID = intent.extras?.getInt("id")!!

        questAuthImageListViewAdapter.submitList(QuestListFactory.questAuthItemFactory())

    }
}
