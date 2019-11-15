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
import com.example.flxrexample.quest_model.QuestConstraint
import com.example.flxrexample.quest_model.QuestListFactory
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class QuestOngoingAuthActivity : AppCompatActivity(), QuestAuthEventListener {

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

        questAuthImageListViewAdapter = QuestThumbListViewAdapter(this)

        binding.questOngoingAuthThumbnailList.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        binding.questOngoingAuthThumbnailList.adapter = questAuthImageListViewAdapter
        binding.questOngoingAuthThumbnailList.addItemDecoration(SpacingItemDecoratorBottom(3,40))
        binding.questOngoingAuthThumbnailList.setHasFixedSize(true)

        questAuthImageListViewAdapter.submitList(QuestListFactory.questAuthItemFactory())

        binding.questOngoingAuthSubmitBtn.setOnClickListener {
            viewModel.addQuestAuthImages(questID)
            val intent = Intent(this,QuestOngoingReviewActivity::class.java)
            intent.putExtra("id",questID)
            startActivity(intent)
        }

        questID = intent.extras?.getInt("id")!!

        viewModel.getQuestConstraints(questID).observe(this, Observer<List<QuestConstraint>> { questConstraints ->
            binding.questOngoingMainImageView.setImageListener { position, imageView ->
                Picasso.get().load(questConstraints[position].pictureURL).into(imageView)
                //imageView.setImageResource(sampleImages[position])
            }
            binding.questOngoingMainImageView.pageCount = questConstraints.size
            //binding.questOngoingMainImageView.pageCount = sampleImages.size
        })

        viewModel.getQuestConstraintsCount(questID).observe(this, Observer<Int> { authImageSize ->
            val tmp = QuestListFactory.questDummyAuthItemFactory(authImageSize)
            tmp.forEach { viewModel.addQuestAuthImage(it) }
            questAuthImageListViewAdapter.submitList(tmp)
        })

        viewModel.getImageURL().observe(this, Observer<String> {url ->
            viewModel.addQuestAuthImageURL(questAuthImageItem, url)
        })

        viewModel.getAllAuthImageLiveData().observe(this, Observer<ArrayList<QuestAuthImage>> { questAuthImages ->
            questAuthImageListViewAdapter.submitList(questAuthImages.toList())
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            //To get the File for further usage
            val auxFile = File(mCurrentPhotoPath)
            viewModel.setImageURL(auxFile.absolutePath)
        }else if(requestCode == Activity.RESULT_CANCELED){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    takePicture()

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }

    override fun addPicture(item: QuestAuthImage) {

        if (checkPersmission()) {
            takePicture()
        }
        else
            requestPermission()

        questAuthImageItem = item
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ), PERMISSION_REQUEST_CODE)
    }


    private fun takePicture() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()

        val uri: Uri = FileProvider.getUriForFile(
            this,
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
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            mCurrentPhotoPath = absolutePath
        }
    }
}
