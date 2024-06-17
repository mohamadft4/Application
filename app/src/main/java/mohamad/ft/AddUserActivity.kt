package mohamad.ft

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import mohamad.ft.databinding.ActivityAddUserBinding
import mohamad.ft.db.UserDatabase
import mohamad.ft.db.UserEntity
import mohamad.ft.utils.Constants

class AddUserActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityAddUserBinding

    //Database
    private val userDb: UserDatabase by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, Constants.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom()
            .build()
    }

    //User
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            //Click
            btnSave.setOnClickListener {
                val name = etName.editText?.text.toString()
                val age = etAge.editText?.text.toString()
                if (name.isNotEmpty() || age.isNotEmpty()) {
                    user = UserEntity(id = 0, name = name, age = age.toInt())
                    userDb.userDao().insertUser(user)
                    finish()
                } else {
                    etName.error = "Name is required"
                    etAge.error = "Age is required"
                }

            }


        }

    }
}