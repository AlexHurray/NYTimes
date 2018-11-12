package com.example.ermolaenkoalex.nytimes.db;

import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    Observable<List<NewsItem>> getAll();

    @Query("SELECT * FROM news WHERE id = :id")
    Observable<NewsItem> getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsItem... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(NewsItem item);

    @Query("DELETE FROM news WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM news")
    void deleteAll();
}
