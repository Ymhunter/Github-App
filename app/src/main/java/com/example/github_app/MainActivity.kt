package com.example.github_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.github_app.Database.MyDBConnection
import com.example.github_app.Repo.RepositoriesList
import com.example.github_app.User.UserInfoActivity
import com.example.github_app.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSearchListener()
        moveToProfileUser()
        configureToolBar()
    }

    private fun configureToolBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = ""
    }
    private fun setSearchListener() {
        binding.searchBar.setOnEditorActionListener { textView, i, keyEvent ->

            if (i == EditorInfo.IME_ACTION_SEARCH) {
                val userName = binding.searchBar.text.toString()
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val dbcon: MyDBConnection = MyDBConnection(this@MainActivity, "UserDBInfo", 1)
                val currentDate = sdf.format(Date())

                if (!binding.searchBar.text.isEmpty()) {
                    val result= dbcon.insert(userName, currentDate.toString())
                    if (result.equals(-1))
                    {
                        Toast.makeText(applicationContext, "Record not inserted", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Record inserted", Toast.LENGTH_SHORT).show()
                    }
                }else
                {
                    Toast.makeText(applicationContext,  "Please insert values first", Toast.LENGTH_SHORT).show()
                }

                moveToUserActivity(userName)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun moveToProfileUser() {
        binding.kotlinLang.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "kotlin="))
        }
        binding.pythonLang.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "python="))
        }
        binding.javaLang.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "java="))
        }
        binding.cPlus.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "cplusplus="))
        }
        binding.jetBrainsOrg.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "jetbrains"))
        }
        binding.githubOrg.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "github"))
        }
        binding.googleOrg.setOnClickListener {
            startActivity(RepositoriesList.intent(this, "google"))
        }
        binding.hlineroUser.setOnClickListener {
            startActivity(UserInfoActivity.intent(this, "hlinero"))
        }
        binding.torvaldsUser.setOnClickListener {
            startActivity(UserInfoActivity.intent(this, "torvalds"))
        }
        binding.gaearonUser.setOnClickListener {
            startActivity(UserInfoActivity.intent(this, "gaearon"))
        }
    }

    private fun moveToUserActivity(user: String) {
        startActivity(UserInfoActivity.intent(this, user))
    }
    companion object {
        private const val KEY_USER = "USER_DETAILS"
    }
}
