package com.example.flxrexample.quest_ongoing

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

object QuestOngoingBindingAdapter {
    @BindingAdapter("android:layout_marginBottom")
    @JvmStatic
    fun setLayoutMarginBottom(view: ConstraintLayout, margin: Float) {
        val lp = view.layoutParams as MarginLayoutParams
        lp.setMargins(0, 0, 0, margin.toInt())
        view.layoutParams = lp
    }

}