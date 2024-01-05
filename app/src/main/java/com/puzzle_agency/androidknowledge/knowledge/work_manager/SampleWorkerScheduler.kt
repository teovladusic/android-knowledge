package com.puzzle_agency.androidknowledge.knowledge.work_manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest

class SampleWorkerScheduler {

    fun scheduleSampleWorkWhenNetworkAvailable(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest: WorkRequest = OneTimeWorkRequestBuilder<SampleWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
