package com.sergiodan.transformerbattle.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Context.getColor(attr: Array<Int>): Int {
    val textSizeAttr = attr.toIntArray()
    val indexOfAttr = 0
    val typedValue = TypedValue()
    val a: TypedArray = this.obtainStyledAttributes(typedValue.data, textSizeAttr)
    val dimension: Int = a.getColor(indexOfAttr, -1)
    a.recycle()
    return dimension
}

fun Fragment.hideKeyboard() {
    this.view?.rootView?.windowToken.let { v ->
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v, 0)
    }
}