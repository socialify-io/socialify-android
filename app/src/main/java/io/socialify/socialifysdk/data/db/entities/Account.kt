package io.socialify.socialifysdk.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "Accounts",
    indices = [Index(
        value = ["username", "deviceId", "userId"],
        unique = true
    )]
)
data class Account(
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "deviceId")
    val deviceId: Int,

    @ColumnInfo(name = "userId")
    val userId: Long
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
