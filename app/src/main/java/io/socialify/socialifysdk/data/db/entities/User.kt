package io.socialify.socialifysdk.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "Users",
    indices = [Index(
        value = ["username", "deviceId", "userId"],
        unique = true
    )]
)
data class User(
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "deviceId")
    val deviceId: Int,

    @ColumnInfo(name = "userId")
    val userId: Int
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
