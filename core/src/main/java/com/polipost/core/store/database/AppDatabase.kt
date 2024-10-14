package com.polipost.core.store.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.polipost.core.store.database.dao.UserDao
import com.polipost.core.store.database.entities.UserEntity


@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}