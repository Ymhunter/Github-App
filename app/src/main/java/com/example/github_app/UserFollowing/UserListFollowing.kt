package com.example.github_app.UserFollowing

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
import com.example.github_app.Commen.UserFollowing
import com.example.github_app.R
import com.example.github_app.User.UserInfoActivity
import com.example.github_app.databinding.ActivityUserListFollowingBinding
import kotlinx.coroutines.launch

class UserListFollowing : AppCompatActivity() {
    private lateinit var binding: ActivityUserListFollowingBinding
    private val viewModel: UserFollowingViewModel by viewModels { UserFollowingViewModel.factory(userName()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_following)
        binding = ActivityUserListFollowingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observerState()
        configureToolBar()
    }
    private fun configureToolBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            this.title = "Following"
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
                viewModel.state.collect {
                    when (it) {
                        UserFollowingState.Loading -> {}
                        is UserFollowingState.Success -> {
                            showUserFollowing(it.userFollowingDetails)
                        }
                        UserFollowingState.Failure -> {}
                    }
                }
            }
        }
    }
    private fun userName(): String {
        return intent.getStringExtra(KEY_FOLLOWING_USER)!!
    }

    private fun showUserFollowing(user: List<UserFollowing>) {
        binding.recyclerView.apply {
            visibility = View.VISIBLE
            addItemDecoration(DividerItemDecoration(this@UserListFollowing, LinearLayout.VERTICAL))
            adapter = UserFollowingAdapter(items = user, onCellClicked = ::showUserFollowingDetails)
            layoutManager = LinearLayoutManager(this@UserListFollowing)
            setHasFixedSize(true)
        }
    }
    private fun showUserFollowingDetails(user: String) {
        val intent = UserInfoActivity.intent(this, user)
        startActivity(intent)
    }
    companion object {
        private const val KEY_FOLLOWING_USER = "USER_FOLLOWING_DETAILS"

        fun intent(context: Context, userName: String): Intent {
            val intent = Intent(context, UserListFollowing::class.java)
            intent.putExtra(KEY_FOLLOWING_USER, userName)
            return intent
        }
    }
}
