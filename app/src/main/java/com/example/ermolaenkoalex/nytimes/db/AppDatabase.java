package com.example.ermolaenkoalex.nytimes.db;

import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "NewsRoomDb.db";

    public abstract NewsDao newsDao();
}