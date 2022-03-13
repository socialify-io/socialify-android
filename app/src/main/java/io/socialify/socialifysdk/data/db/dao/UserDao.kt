package io.socialify.socialifysdk.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import io.socialify.socialifysdk.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = ABORT)
    suspend fun insertAll(user: User)
}
