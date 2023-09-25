package com.imranmelikov.kotlinworkmanager

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDataBase(var context: Context, workerParams: WorkerParameters) :Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val getData=inputData
        val myNumber=getData.getInt("inputKey",0)
        refreshData(myNumber)
        return Result.success()
    }

    private fun refreshData(myGetNumber:Int){
        val sharedPreferences=context.getSharedPreferences("com.imranmelikov.kotlinworkmanager",Context.MODE_PRIVATE)
        var myNumber=sharedPreferences.getInt("myNumber",0)
        myNumber += myGetNumber
        println(myNumber)
        sharedPreferences.edit().putInt("myNumber",myNumber).apply()
    }
}