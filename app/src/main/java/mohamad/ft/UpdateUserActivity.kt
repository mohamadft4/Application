package mohamad.ft

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import mohamad.ft.databinding.ActivityUpdateUserBinding
import mohamad.ft.db.UserDatabase
import mohamad.ft.db.UserEntity
import mohamad.ft.utils.Constants
import mohamad.ft.utils.Constants.USER_ID

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var name: String
    private var age: Int = -1
    private var id: Int = -1

    //Database
    private val userDb: UserDatabase by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, Constants.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            id = it.getInt(USER_ID, -1)
        }

        binding.apply {
            name = userDb.userDao().getUserById(id).name
            age = userDb.userDao().getUserById(id).age
            etName.editText?.setText(name)
            etAge.editText?.setText(age.toString())
            btnUpdate.setOnClickListener {
                if (etName.editText?.text.toString().isNotEmpty() &&
                    etAge.editText?.text.toString().isNotEmpty()
                ) {
                    userDb.userDao().updateUser(
                        UserEntity(
                            id,
                            etName.editText?.text.toString(),
                            etAge.editText?.text.toString().toInt()
                        )
                    )
                    finish()

                } else {
                    etName.error = "Name is required"
                    etAge.error = "Age is required"
                }
            }

        }

    }
}