package com.example.flxrexample.charge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.customradiobutton.radiobutton.CustomRadioGroup
import com.example.customradiobutton.radiobutton.OnCustomRadioButtonListener
import com.example.customradiobutton.radiobutton.StarCustomRadioButton
import com.example.flxrexample.R
import com.example.flxrexample.databinding.ActivityStarChargeBinding
import com.example.flxrexample.quest_model.StarAccount

class StarChargeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStarChargeBinding
    private lateinit var viewModel: StarChargeViewModel

    private lateinit var starAccount: StarAccount

    private var beforeStarAmount = 0
    private var addedStarAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(StarChargeViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityStarChargeBinding>(this,
            R.layout.activity_star_charge)

        binding.starChargePaymentChargeSubmitBtn.setOnClickListener {
            val afterStarAmount = beforeStarAmount + addedStarAmount
            viewModel.updateStarAccount(starAccount, afterStarAmount)
            binding.starChargePaymentMoneyAfterStarCount.text = "${afterStarAmount} 스타"
            val intent = Intent(this, StarChargeCompleteActivity::class.java)
            startActivity(intent)
        }

        CustomRadioGroup.setOnClickListener(object : OnCustomRadioButtonListener {
            override fun onClick(view: View) {
                when(view.id) {
                    R.id.star_btn_1 -> { viewModel.setAddedStar(50) }
                    R.id.star_btn_2 -> { viewModel.setAddedStar(100) }
                    R.id.star_btn_3 -> { viewModel.setAddedStar(300) }
                    R.id.star_btn_4 -> { viewModel.setAddedStar(500) }
                    R.id.star_btn_5 -> { viewModel.setAddedStar(1000) }
                    R.id.star_btn_6 -> { viewModel.setAddedStar(3000) }
                    R.id.star_btn_7 -> { viewModel.setAddedStar(5000) }
                    R.id.star_btn_8 -> { viewModel.setAddedStar(10000) }
                    R.id.star_btn_9 -> { viewModel.setAddedStar(50000) }
                    R.id.star_btn_10 -> { viewModel.setAddedStar(100000) }
                }
            }
        })

        viewModel.getStarAccount().observe(this, Observer<StarAccount> { starAccount ->
            this.starAccount = starAccount
            beforeStarAmount = starAccount.starAmount
            binding.starChargePaymentMoneyBeforeStarCount.text = "${beforeStarAmount} 스타"
            binding.starChargePaymentMoneyAfterStarCount.text = "${beforeStarAmount} 스타"
        })

        viewModel.getAddedStar().observe(this, Observer<Int> { addedStar ->
            this.addedStarAmount = addedStar
            binding.starChargePaymentMoneyAfterStarCount.text = "${beforeStarAmount + addedStar} 스타"
        })
    }
}
