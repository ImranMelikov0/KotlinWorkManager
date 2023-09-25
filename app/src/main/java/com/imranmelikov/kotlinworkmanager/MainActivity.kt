package com.imranmelikov.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val putInt=Data.Builder().putInt("inputKey",1).build()

        val constraints=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        //One Time
//        val workRequest:WorkRequest= OneTimeWorkRequestBuilder<RefreshDataBase>()
//            .setConstraints(constraints)
//            .setInputData(putInt)
////        .setInitialDelay(5,TimeUnit.MINUTES )
//            .addTag("myTag")
//            .build()
//
//        WorkManager.getInstance(this).enqueue(workRequest)

        val workRequest:PeriodicWorkRequest= PeriodicWorkRequestBuilder<RefreshDataBase>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(putInt)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
            if (it.state==WorkInfo.State.RUNNING){
                println("running")
            }else if(it.state==WorkInfo.State.FAILED){
                println("failed")
            }else if(it.state==WorkInfo.State.SUCCEEDED){
                println("success")
            }
        })

//        WorkManager.getInstance(this).cancelAllWork()

        //Chaining
//        val oneTimeWorkRequest:OneTimeWorkRequest= OneTimeWorkRequestBuilder<RefreshDataBase>()
//            .setConstraints(constraints)
//            .setInputData(putInt)
//            .build()
//
//        WorkManager.getInstance(this)
//            .beginWith(oneTimeWorkRequest)
//            .then(oneTimeWorkRequest)
//            .then(oneTimeWorkRequest)
//            .enqueue()

    }

}