package com.puzzle_agency.androidknowledge.knowledge.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.formatAsPrice(): String {
    val decimalFormat = DecimalFormat("###,###.###", DecimalFormatSymbols(Locale.GERMAN))
    decimalFormat.minimumFractionDigits = 2
    return decimalFormat.format(this)
}