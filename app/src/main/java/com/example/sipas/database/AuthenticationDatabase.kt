package com.example.sipas.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sipas.dao.AuthenticationDao
import com.example.sipas.model.Authentication
import com.example.sipas.model.Response

@Database(entities = [Authentication::class], version = 1)
abstract class AuthenticationDatabase: RoomDatabase() {

    abstract fun authenticationDao(): AuthenticationDao

    companion object{
        @Volatile
        private var INSTANCE: AuthenticationDatabase? = null

        fun getInstance(context: Context): AuthenticationDatabase{

            INSTANCE?.let {
                return it
            }

            INSTANCE = Room
                .databaseBuilder(context, AuthenticationDatabase::class.java, "authentication_database")
                .fallbackToDestructiveMigration()
                .build()

            Log.d("AuthenticationDatabase", "INSTANCE = $INSTANCE")

            return INSTANCE!!
        }
    }
}