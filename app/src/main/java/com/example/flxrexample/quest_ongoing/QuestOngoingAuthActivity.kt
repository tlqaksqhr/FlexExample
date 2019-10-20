package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.activity_quest_ongoing_auth.*

class QuestOngoingAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_ongoing_auth)

        quest_ongoing_auth_submit_btn.setOnClickListener {
            val questReviewIntent = Intent(this, QuestOngoingReviewActivity::class.java)
            startActivity(questReviewIntent)
        }

    }
}
