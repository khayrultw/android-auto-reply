package com.khayrul.doauto.data.data_source

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khayrul.doauto.domain.model.AutoReplyRule

@Database(
    entities = [AutoReplyRule::class],
    version = 1
)
abstract class DoautoDatabase: RoomDatabase() {

    abstract val autoReplyRuleDao: AutoReplyRuleDao

    companion object {
        private const val DATABASE_NAME = "do_auto_database"
        private var database: DoautoDatabase? = null

        fun getInstance(app: Application): DoautoDatabase {
            if(database == null) {
                database = Room.databaseBuilder(
                    app,
                    DoautoDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return database as DoautoDatabase
        }
    }
}