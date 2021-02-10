package me.vigroid.workmanagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import me.vigroid.workmanagerdemo.worker.UploadWorker

class MainActivity : AppCompatActivity() {
    companion object {
        private const val UPLOAD_UNIQUE_NAME = "uploadData"
    }

    private val uploadWork = OneTimeWorkRequestBuilder<UploadWorker>()
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        WorkManager.getInstance(applicationContext).cancelUniqueWork(UPLOAD_UNIQUE_NAME)
        // update
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            UPLOAD_UNIQUE_NAME,
            ExistingWorkPolicy.KEEP,
            uploadWork
        )
    }
}