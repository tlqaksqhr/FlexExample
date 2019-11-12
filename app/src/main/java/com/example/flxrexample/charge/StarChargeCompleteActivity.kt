package com.example.flxrexample.charge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.flxrexample.MainActivity
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityStarChargeCompleteBinding
import com.example.flxrexample.quest_model.StarAccount

class StarChargeCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStarChargeCompleteBinding
    private lateinit var viewModel: StarChargeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StarChargeViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityStarChargeCompleteBinding>(this,
            R.layout.activity_star_charge_complete)

        viewModel.getStarAccount().observe(this, Observer<StarAccount> { starAccount ->
            binding.starChargeCompleteCurrentStarCount.text = "${starAccount.starAmount} 스타"
        })

        binding.starChargeCompleteBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
