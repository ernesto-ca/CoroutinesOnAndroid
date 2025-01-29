package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AndroidVersionDao {

    @Query("SELECT * FROM androidversions")
    fun getAndroidVersions(): List<AndroidVersionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(androidVersionEntity: AndroidVersionEntity)

    @Query("DELETE FROM androidversions")
    fun clear()
}