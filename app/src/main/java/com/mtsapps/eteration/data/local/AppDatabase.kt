package com.mtsapps.eteration.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mtsapps.eteration.data.local.entity.CartEntity

@Database(entities = [CartEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}