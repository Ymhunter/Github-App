package com.example.github_app.UserFollowers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_app.Commen.UserFollow
import com.example.github_app.R
import com.example.github_app.User.UserInfoActivity
import com.example.github_app.databinding.ActivityUserFollowBinding
import kotlinx.coroutines.launch

class UserListFollow : AppCompatActivity() {
    private lateinit var binding: ActivityUserFollowBinding
    private val viewModel: UserFollowViewModel by viewModels { UserFollowViewModel.factory(userName()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_follow)
        binding = ActivityUserFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observerState()
        configureToolBar()
    }
    private fun configureToolBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            this.title = "Followers"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun observerState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state) {
                        UserFollowState.Loading -> {}
                        is UserFollowState.Success -> {
                            showUser(state.userFollowDetails)
                        }
                        UserFollowState.Failure -> {}
                        else -> {}
                    }
                }
            }
        }
    }
    private fun userName(): String {
        return intent.getStringExtra(KEY_FOLLOW_USER)!!
    }

    private fun showUser(user: List<UserFollow>) {
        binding.recyclerView.apply {
            visibility = View.VISIBLE
            addItemDecoration(DividerItemDecoration(this@UserListFollow, LinearLayout.VERTICAL))
            adapter = UserAdapter(items = user, onCellClicked = ::showUserFollowersDetails)
            layoutManager = LinearLayoutManager(this@UserListFollow)
            setHasFixedSize(true)
        }
    }

    private fun showUserFollowersDetails(user: String) {
        val intent = UserInfoActivity.intent(this, user)
        startActivity(intent)
    }
    companion object {
        private const val KEY_FOLLOW_USER = "USER_FOLLOW_DETAILS"

        fun intent(context: Context, userName: String): Intent {
            val intent = Intent(context, UserListFollow::class.java)
            intent.putExtra(KEY_FOLLOW_USER, userName)
            return intent
        }
    }
}
