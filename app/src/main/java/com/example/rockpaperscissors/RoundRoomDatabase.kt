package com.example.rockpaperscissors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Round::class], version = 1, exportSchema = false)
abstract class RoundRoomDatabase : RoomDatabase() {

    abstract fun roundDao(): RoundDao

    companion object {
        private const val DATABASE_NAME = "ROUND_DATABASE"

        @Volatile
        private var roundRoomDatabaseInstance: RoundRoomDatabase? = null

        fun getDatabase(context: Context): RoundRoomDatabase? {
            if (roundRoomDatabaseInstance == null) {
                synchronized(RoundRoomDatabase::class.java) {
                    if (roundRoomDatabaseInstance == null) {
                        roundRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            RoundRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return roundRoomDatabaseInstance
        }
    }

}
