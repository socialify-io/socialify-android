package io.socialify.socialifysdk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.socialify.socialifysdk.data.db.dao.AccountDao
import io.socialify.socialifysdk.data.db.dao.DMDao
import io.socialify.socialifysdk.data.db.dao.UserDao
import io.socialify.socialifysdk.data.db.entities.Account
import io.socialify.socialifysdk.data.db.entities.DM
import io.socialify.socialifysdk.data.db.entities.User

@Database(
    entities = [
        Account::class,
        User::class,
        DM::class],
    version = AppDatabase.VERSION_SCHEMA
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val VERSION_SCHEMA = 1
    }

    abstract val accountDao: AccountDao
    abstract val userDao: UserDao
    abstract val dmDao: DMDao
}
