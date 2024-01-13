package com.puzzle_agency.androidknowledge.knowledge.util

import java.util.Locale

object TimeConverter {

    fun formatMillis(millis: Double): String {
        val seconds = millis / 1000

        if (seconds < 0) {
            return "Invalid input: seconds should be non-negative."
        }

        val hours = (seconds / 3600).toInt()
        val minutes = ((seconds % 3600) / 60).toInt()
        val remainingSeconds = (seconds % 60).toInt()

        val hoursPart = if (hours > 0) String.format(Locale.getDefault(), "%02d:", hours) else ""

        val minutesPart = String.format(
            Locale.getDefault(),
            "%02d:",
            minutes
        )

        val secondsPart = String.format(Locale.getDefault(), "%02d", remainingSeconds)

        return "$hoursPart$minutesPart$secondsPart"
    }
}
