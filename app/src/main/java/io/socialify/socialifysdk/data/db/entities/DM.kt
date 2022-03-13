package io.socialify.socialifysdk.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "DMs",
    indices = [Index(
        value = ["user",
            "receiver",
            "sender",
            "message",
            "username",
            "isRead",
            "date"]
    )]
)
data class DM (
    @ColumnInfo(name = "user")
    val userId: Long,

    @ColumnInfo(name = "receiver")
    val receiver: Long,

    @ColumnInfo(name = "sender")
    val sender: Long,

    @ColumnInfo(name = "message")
    val message: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "isRead")
    val isRead: Boolean,

    @ColumnInfo(name = "date")
    val date: String
): Serializable {
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0
}
