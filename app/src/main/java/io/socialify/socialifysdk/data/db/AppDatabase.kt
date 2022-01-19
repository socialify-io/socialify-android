package io.socialify.socialifysdk.data.db

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import io.socialify.socialifysdk.data.db.dao.UserDao
import io.socialify.socialifysdk.data.db.entities.User

@Database(
    entities = [
        User::class],
    version = AppDatabase.VERSION_SCHEMA
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val VERSION_SCHEMA = 1
    }

    abstract val userDao: UserDao
}
