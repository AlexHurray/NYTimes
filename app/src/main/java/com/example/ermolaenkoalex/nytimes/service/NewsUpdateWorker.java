package com.example.ermolaenkoalex.nytimes.service;

import android.content.Context;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NewsUpdateWorker extends Worker {
    private Context context;

    public NewsUpdateWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @Override
    public Result doWork() {
        UpdateService.start(context);
        return Result.SUCCESS;
    }
}
