package io.socialify.socialifysdk.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.socialify.socialifysdk.data.db.entities.DM

@Dao
interface DMDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(dm: DM)

    @Query("SELECT * FROM DMs --WHERE sender=:account AND receiver=:receiver OR sender=:receiver AND receiver=:account")
    fun getDMs(/*account: Long, receiver: Int*/): LiveData<List<DM>>
}
