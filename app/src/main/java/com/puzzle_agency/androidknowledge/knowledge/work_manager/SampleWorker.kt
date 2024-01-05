package com.puzzle_agency.androidknowledge.knowledge.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SampleWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            doSampleWork()
            Result.success()
        } catch (exception: Exception) {
            Log.e("SampleWorker", "Error doing do the work", exception)
            Result.failure()
        }
    }

    private suspend fun doSampleWork() {
        println("doing the sample work")
        delay(5_000)
        println("sample work finished")
    }
}
