package com.puzzle_agency.androidknowledge.knowledge.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberFormatter {

    fun formatAsPrice(
        number: Number,
        pattern: String = "###,###.###",
        minFractionDigits: Int = 2
    ): String {
        val decimalFormat = DecimalFormat(pattern, DecimalFormatSymbols(Locale.GERMAN))
        decimalFormat.minimumFractionDigits = minFractionDigits
        return decimalFormat.format(number)
    }
}
