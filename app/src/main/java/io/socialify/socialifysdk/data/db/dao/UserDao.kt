package io.socialify.socialifysdk.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import io.socialify.socialifysdk.data.db.entities.User

@Dao
abstract class UserDao {
    @Insert(onConflict = ABORT)
    abstract suspend fun insertAll(user: User)

    @Query("SELECT * FROM Users")
    abstract suspend fun getAll(): List<User>

    @Delete
    abstract fun delete(user: User)
}
