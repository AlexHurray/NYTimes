package com.example.ermolaenkoalex.nytimes.di;

import android.content.Context;

import com.example.ermolaenkoalex.nytimes.db.AppDatabase;
import com.example.ermolaenkoalex.nytimes.db.NewsDao;

import androidx.annotation.NonNull;
import androidx.room.Room;
import toothpick.config.Module;

public class DbModule extends Module {

    @NonNull
    private final Context context;

    public DbModule(Context context) {
        this.context = context;
        bind(NewsDao.class).toInstance(provideDao());
    }

    @NonNull
    private NewsDao provideDao() {
        final AppDatabase database = Room.databaseBuilder(context,
                AppDatabase.class,
                AppDatabase.DATABASE_NAME)
                .build();

        return database.newsDao();
    }
}