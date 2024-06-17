package mohamad.ft.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import mohamad.ft.utils.Constants

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllUsers(): MutableList<UserEntity>

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE id = :id")
    fun getUserById(id: Int): UserEntity

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    fun deleteAllUsers()


}