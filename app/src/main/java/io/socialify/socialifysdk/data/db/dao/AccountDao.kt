package io.socialify.socialifysdk.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import io.socialify.socialifysdk.data.db.entities.Account

@Dao
interface AccountDao {
    @Insert(onConflict = ABORT)
    suspend fun insertAll(account: Account)

    @Query("SELECT * FROM Accounts")
    fun getAll(): List<Account>

    @Delete
    fun delete(account: Account)

}
