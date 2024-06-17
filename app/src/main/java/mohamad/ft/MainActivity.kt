package mohamad.ft

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import mohamad.ft.databinding.ActivityMainBinding
import mohamad.ft.db.UserDatabase
import mohamad.ft.utils.Constants

class MainActivity : AppCompatActivity() {
    //Database
    private val userDb: UserDatabase by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, Constants.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom()
            .build()
    }

    //Binding
    private lateinit var binding: ActivityMainBinding

    //Adapter
    private val userAdapter by lazy { UserAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnAdd.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
            }
        }

    }

    private fun checkItem() {
        if (userDb.userDao().getAllUsers().isNotEmpty()) {
            binding.tvShow.visibility = View.GONE
            binding.rcMain.visibility = View.VISIBLE
            //set data
            userAdapter.differ.submitList(userDb.userDao().getAllUsers())
            setupRecyclerView()
        } else {
            binding.tvShow.visibility = View.VISIBLE
            binding.rcMain.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rcMain.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        userAdapter.apply {
            onItemLongClick = {
                userDb.userDao().deleteUser(userDb.userDao().getUserById(it))
                checkItem()
            }
            onItemClick = {
                val intent = Intent(this@MainActivity, UpdateUserActivity::class.java)
                intent.putExtra(Constants.USER_ID, it)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

}