package com.sergiodan.transformerbattle.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue

fun Context.getColor(attr: Array<Int>): Int {
    val textSizeAttr = attr.toIntArray()
    val indexOfAttr = 0
    val typedValue = TypedValue()
    val a: TypedArray = this.obtainStyledAttributes(typedValue.data, textSizeAttr)
    val dimension: Int = a.getColor(indexOfAttr, -1)
    a.recycle()
    return dimension
}