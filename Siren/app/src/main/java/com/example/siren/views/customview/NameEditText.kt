package com.example.siren.views.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.siren.R

class NameEditText: AppCompatEditText, View.OnTouchListener {

    private lateinit var userIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setBackgroundResource(R.drawable.bg_edit_text)
        setOnTouchListener(this)

        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME

        hint = context.getString(R.string.name)


        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    error = context.getString(R.string.field_empty)
                }
            }
        })
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

    private fun setButtonDrawables(
        startOfText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText,
        )
    }
}