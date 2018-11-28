package com.example.ermolaenkoalex.nytimes.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.ermolaenkoalex.nytimes.AppDelegate;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.api.NewsEndpoint;
import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.ui.main.MainActivity;
import com.example.ermolaenkoalex.nytimes.utils.DownloadingNews;
import com.example.ermolaenkoalex.nytimes.utils.NetworkUtils;
import com.example.ermolaenkoalex.nytimes.utils.SharedPreferencesHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

public class UpdateService extends Service {

    private static final String LOG_TAG = "UpdateService";
    private static final String CHANNEL_ID = "CHANNEL_UPDATE_NEWS";
    private static final int UPDATE_NOTIFICATION_ID = 42;

    @NonNull
    @Inject
    protected NewsRepository repository;

    @NonNull
    @Inject
    NewsEndpoint news;

    @NonNull
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    @NonNull
    NetworkUtils networkUtils;

    private Disposable downloadDisposable;

    private PendingIntent onClickPendingIntent;

    public static void start (@NonNull Context context){
        Intent serviceIntent = new Intent(context, UpdateService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toothpick.inject(this, AppDelegate.getAppScope());

        downloadDisposable = networkUtils.getOnlineNetwork()
                .timeout(1, TimeUnit.MINUTES)
                .flatMapCompletable(aBoolean -> DownloadingNews.updateNews(news, repository, sharedPreferencesHelper.getSection()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            showResultNotification(getString(R.string.service_result_success));
                            stopForeground(false);
                        }
                        , throwable -> {
                            Log.e(LOG_TAG, throwable.toString());
                            showResultNotification(getString(R.string.service_result_fail));
                            stopForeground(false);
                        });

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        onClickPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent stopIntent = new Intent(this, StopUpdateServiceReceiver.class);
        PendingIntent stopPendingIntent =
                PendingIntent.getBroadcast(this, 0, stopIntent, 0);

        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.service_notification_title))
                .setContentText(getString(R.string.service_notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(onClickPendingIntent)
                .addAction(R.drawable.ic_error, getString(R.string.service_action_cancel), stopPendingIntent)
                .build();

        startForeground(UPDATE_NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        if (downloadDisposable != null && !downloadDisposable.isDisposed()) {
            downloadDisposable.dispose();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.service_notification_title);
            String description = getString(R.string.service_notification_channel_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showResultNotification(@NonNull String message) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.service_notification_title))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(onClickPendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(UPDATE_NOTIFICATION_ID, notification);
        }
    }
}

