package com.example.bmimolotkov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Weight::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class DatabaseApp : RoomDatabase() {

    abstract fun getWeightDao(): WeightDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseApp? = null

        fun getInstance(context: Context): DatabaseApp {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseApp::class.java,
                    "body_weight_db"
                )
                    .build()

                INSTANCE = instance
            }

            return instance
        }
    }
}