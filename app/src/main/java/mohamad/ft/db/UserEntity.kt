package mohamad.ft.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import mohamad.ft.utils.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var age: Int
)
