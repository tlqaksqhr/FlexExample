package com.example.customradiobutton.radiobutton

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.res.TypedArray
import android.view.LayoutInflater
import androidx.annotation.StyleableRes
import com.google.android.material.card.MaterialCardView


abstract class BaseCustomRadioButton : MaterialCardView {


    protected var attrs: AttributeSet? = null
    protected var a: TypedArray? = null
    protected var styleable: IntArray? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, layoutResId: Int, styleable: IntArray?) : super(context) {
        initLayoutResId(layoutResId)
        initStyleable(styleable)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?,
        layoutResId: Int, styleable: IntArray?) : super(context, attrs) {

        initLayoutResId(layoutResId)
        initStyleable(styleable)
        initAttrs(attrs)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int,
                layoutResId: Int, styleable: IntArray?) : super(context, attrs, defStyleAttr) {
        initLayoutResId(layoutResId)
        initStyleable(styleable)
        initAttrs(attrs)
        initView()
    }


    private fun initLayoutResId(layoutResId: Int){
        LayoutInflater.from(context).inflate(layoutResId,this,true)
    }


    private fun initAttrs(attrs: AttributeSet?){
        this.attrs = attrs
    }

    private fun initStyleable(styleable: IntArray?) {
        this.styleable = styleable
    }

    private fun initView() {
        initTypedArray()
        bindViews()
        initAttributes()
        populateViews()
    }


    private fun initTypedArray() {
        a = context.theme.obtainStyledAttributes(attrs, styleable, 0,0 )
    }

    protected abstract fun bindViews()

    protected abstract fun initAttributes()

    protected abstract fun populateViews()
}