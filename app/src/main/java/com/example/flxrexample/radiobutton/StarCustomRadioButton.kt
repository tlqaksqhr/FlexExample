package com.example.customradiobutton.radiobutton

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.example.flxrexample.R
import kotlinx.android.synthetic.main.custom_star_button.view.*

class StarCustomRadioButton : BaseCustomRadioButton {

    private lateinit var starCountTextView: TextView
    private lateinit var starPriceTextView: TextView
    private lateinit var starBonusTextView: TextView

    private lateinit var starCount : String
    private lateinit var starPrice : String
    private lateinit var starBonus : String

    constructor(context: Context) :
        super(context, R.layout.custom_star_button,
        R.styleable.CustomStarRadioButton)

    constructor(context: Context, attrs: AttributeSet)
        : super(context, attrs, R.layout.custom_star_button,
        R.styleable.CustomStarRadioButton)

    constructor(context: Context, attrs: AttributeSet,
        defStyleAttr: Int) : super(context, attrs,
        defStyleAttr,R.layout.custom_star_button,
        R.styleable.CustomStarRadioButton)


    override fun bindViews() {
        starCountTextView = star_count_btn_count
        starPriceTextView = star_count_btn_price
        starBonusTextView = star_count_btn_bonus
    }

    override fun initAttributes() {
        initStarCount(R.styleable.CustomStarRadioButton_star_button_count)
        initStarPrice(R.styleable.CustomStarRadioButton_star_button_price)
        initStarBonus(R.styleable.CustomStarRadioButton_star_button_bonus)
    }

    override fun populateViews() {
        starCountTextView.text = starCount
        starPriceTextView.text = starPrice
        starBonusTextView.text = starBonus
    }

    private fun initStarCount(index: Int) {
        if(typedArrayHasValue(index)) {
            starCount = a?.getString(index)!!
            if(starCount.length == 6){
                starCountTextView.textSize = 17.5f
                starCountTextView.letterSpacing = -0.05f
                starCountTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    -0.5f,  resources.displayMetrics), 1.0f)
            }else if(starCount.length == 7){
                starCountTextView.textSize = 15.0f
                starCountTextView.letterSpacing = -0.05f
                //starCountTextView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                //    -2.5f,  resources.displayMetrics), 1.0f)
            }
        }
    }

    private fun initStarPrice(index: Int) {
        if(typedArrayHasValue(index)) {
            starPrice = a?.getString(index)!!
        }

    }

    private fun initStarBonus(index: Int) {
        if(typedArrayHasValue(index)) {
            starBonus = a?.getString(index)!!
        }
    }
    private fun typedArrayHasValue(index: Int) : Boolean {
        return a?.hasValue(index)!!
    }
}