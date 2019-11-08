package com.example.customradiobutton.radiobutton

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.forEach
import kotlinx.android.synthetic.main.custom_star_button.view.*


class CustomRadioGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {

        private var onClickListener: OnCustomRadioButtonListener? = null

        fun setOnClickListener(onClickListener: OnCustomRadioButtonListener) {
            CustomRadioGroup.onClickListener = onClickListener
        }
    }


    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {

        if(child is BaseCustomRadioButton) {
            child.setOnClickListener { view ->
                val selectedButton = child

                setAllButtonsToUnselectedState()
                setSelectedButtonToSelectedState(selectedButton)
                initOnClickListener(selectedButton)
            }
        }

        super.addView(child, index, params)
    }

    private fun setAllButtonsToUnselectedState(){
        val container = this

        container.forEach { child ->
            if(child is BaseCustomRadioButton) {
                setButtonToUnselectedState(child)
            }
        }
    }

    private fun setButtonToUnselectedState(containerView: BaseCustomRadioButton) {
        val viewWithFilter = 1f
        containerView.alpha = viewWithFilter
        containerView.background = Color.rgb(0xFF,0xFF,0xFF).toDrawable()
        containerView.strokeColor = Color.rgb(0xec,0xec,0xec)
        containerView.strokeWidth = 2
        containerView.star_count_btn_count.setTextColor(Color.rgb(0xec,0xec,0xec))
        containerView.star_count_btn_price.setTextColor(Color.rgb(0xec,0xec,0xec))
        containerView.star_count_btn_bonus.setTextColor(Color.rgb(0xec,0xec,0xec))
        containerView.star_count_star_title.setTextColor(Color.rgb(0xec,0xec,0xec))
    }

    private fun setSelectedButtonToSelectedState(selectedButton: BaseCustomRadioButton) {
        val viewWithoutFilter = 1f
        selectedButton.alpha = viewWithoutFilter
        selectedButton.background = Color.rgb(0xFF,0xFF,0xFF).toDrawable()
        selectedButton.strokeColor = Color.rgb(0xfc,0x4d,0x71)
        selectedButton.strokeWidth = 2
        selectedButton.star_count_btn_count.setTextColor(Color.rgb(0xfc,0x4d,0x71))
        selectedButton.star_count_btn_price.setTextColor(Color.rgb(0xfc,0x4d,0x71))
        selectedButton.star_count_btn_bonus.setTextColor(Color.rgb(0xfc,0x4d,0x71))
        selectedButton.star_count_star_title.setTextColor(Color.rgb(0xfc,0x4d,0x71))
    }

    private fun initOnClickListener(selectedButton: View){
        if(onClickListener != null) {
            onClickListener!!.onClick(selectedButton)
        }
    }
}