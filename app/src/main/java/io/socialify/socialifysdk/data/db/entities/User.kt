package io.socialify.socialifysdk.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "Users",
    indices = [Index(
        value = ["username"]
    )]
)
data class User (
    @ColumnInfo(name = "username")
    val username: String
): Serializable {
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0
}
