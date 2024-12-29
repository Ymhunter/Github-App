package com.example.github_app.User

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.github_app.Commen.Repositories
import com.example.github_app.Commen.User
import com.example.github_app.R
import com.example.github_app.Repo.RepositoriesList
import com.example.github_app.UserFollowers.UserListFollow
import com.example.github_app.UserFollowing.UserListFollowing
import com.example.github_app.databinding.ActivityUserInfoBinding
import kotlinx.coroutines.launch

class UserInfoActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private val viewModel: UserViewModel by viewModels { UserViewModel.factory(user()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observerState()
        setFollowListener()
        configureToolBar()
    }
    private fun configureToolBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            this.title = user()
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun user(): String {
        return intent.getStringExtra(KEY_USER_FOLLOW)!!
    }
    private fun setFollowListener() {
        binding.followersCount.setOnClickListener {
            moveUserToFollowList(user())
        }
        binding.followingCount.setOnClickListener {
            moveUserToFollowingList(user())
        }
        binding.repositoriesCount.setOnClickListener {
            moveUserToRepos(user())
        }
    }

    private fun observerState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state) {
                        DetailsState.Failure -> {}
                        DetailsState.Loading -> {}
                        is DetailsState.Success -> {
                            showDetails(details = state.userDetails)
                        }
                        is DetailsState.SuccessRepo -> {
                            showLanguageDetails(details = state.repoTopDetails)
                        }
                    }
                }
            }
        }
    }

    private fun loadImage(url: String) {
        if (url.isEmpty()) {
            binding.image.setImageResource(R.drawable.kotlin_svgrepo_com)
        } else {
            Glide
                .with(binding.image)
                .load(url)
                .placeholder(R.drawable.kotlin_svgrepo_com)
                .centerCrop()
                .into(binding.image)
        }
    }

    private fun showLanguageDetails(details: List<Repositories>) {
        var topLangCount = mutableMapOf<String, Int>()
        for (repo in details) {
            if (repo.language.isNotEmpty()) {
                topLangCount[repo.language] = topLangCount.getOrDefault(repo.language, 0) + 1
            }
        }

        val topLanguages = topLangCount.entries.sortedByDescending { it.value }

        binding.lang1.text = topLanguages.getOrNull(0)?.key ?: ""
        binding.lang2.text = topLanguages.getOrNull(1)?.key ?: ""
        binding.lang3.text = topLanguages.getOrNull(2)?.key ?: ""
        if (binding.lang1.text == "Kotlin") {
            binding.lang1.setBackgroundResource(R.drawable.kotlin_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "Java") {
            binding.lang1.setBackgroundResource(R.drawable.java_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "JavaScript") {
            binding.lang1.setBackgroundResource(R.drawable.javascript_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "C") {
            binding.lang1.setBackgroundResource(R.drawable.c_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "C++") {
            binding.lang1.setBackgroundResource(R.drawable.cplusplus_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "Python") {
            binding.lang1.setBackgroundResource(R.drawable.python_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "TypeScript") {
            binding.lang1.setBackgroundResource(R.drawable.typescript_svgrepo_com)
            binding.lang1.text = ""
        } else if (binding.lang1.text == "CSS") {
            binding.lang1.setBackgroundResource(R.drawable.css_3_svgrepo_com)
            binding.lang1.text = ""
        }

        if (binding.lang2.text == "Kotlin") {
            binding.lang2.setBackgroundResource(R.drawable.kotlin_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "Java") {
            binding.lang2.setBackgroundResource(R.drawable.java_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "JavaScript") {
            binding.lang2.setBackgroundResource(R.drawable.javascript_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "C") {
            binding.lang2.setBackgroundResource(R.drawable.c_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "C++") {
            binding.lang2.setBackgroundResource(R.drawable.cplusplus_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "Python") {
            binding.lang2.setBackgroundResource(R.drawable.python_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "TypeScript") {
            binding.lang2.setBackgroundResource(R.drawable.typescript_svgrepo_com)
            binding.lang2.text = ""
        } else if (binding.lang2.text == "CSS") {
            binding.lang2.setBackgroundResource(R.drawable.css_3_svgrepo_com)
            binding.lang2.text = ""
        }

        if (binding.lang3.text == "Kotlin") {
            binding.lang3.setBackgroundResource(R.drawable.kotlin_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "Java") {
            binding.lang3.setBackgroundResource(R.drawable.java_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "JavaScript") {
            binding.lang3.setBackgroundResource(R.drawable.javascript_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "C") {
            binding.lang3.setBackgroundResource(R.drawable.c_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "C++") {
            binding.lang3.setBackgroundResource(R.drawable.cplusplus_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "Python") {
            binding.lang3.setBackgroundResource(R.drawable.python_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "TypeScript") {
            binding.lang3.setBackgroundResource(R.drawable.typescript_svgrepo_com)
            binding.lang3.text = ""
        } else if (binding.lang3.text == "CSS") {
            binding.lang3.setBackgroundResource(R.drawable.css_3_svgrepo_com)
            binding.lang3.text = ""
        }
    }

    private fun showDetails(details: User) {
        binding.apply {
            userName.text = "${details.name}"
            textViewUserCountry.text = "${details.location}"
            followersCount.text = "${details.followers}"
            followingCount.text = "${details.following}"
            repositoriesCount.text = "${details.repo}"
            bio.text = "${details.blog}"
            loadImage(details.avatar_url)
            twitter.text = "${details.twitter}"
            company.text = "${details.company}"
            hireable.text = "${details.hireable}"
            textViewUserCountry.text = "${details.location}"
        }

        if (binding.twitter.text.isNotEmpty()) {
            binding.twitterIcon.visibility = View.VISIBLE
            binding.twitter.visibility = View.VISIBLE
            binding.twitter.text = "@${binding.twitter.text}"
        }
        if (binding.hireable.text == "false") {
            binding.hireable.visibility = View.VISIBLE
            binding.hireableIcon.visibility = View.VISIBLE
            binding.hireable.text = "not hireable"
        } else {
            binding.hireable.visibility = View.VISIBLE
            binding.hireableIcon.visibility = View.VISIBLE
            binding.hireable.text = "hireable"
        }
        if (binding.company.text.isNotEmpty()) {
            binding.company.visibility = View.VISIBLE
            binding.companyIcon.visibility = View.VISIBLE
        }
        if (binding.email.text.isNotEmpty()) {
            binding.email.visibility = View.VISIBLE
            binding.internetIcon.visibility = View.VISIBLE
        }
    }

    private fun moveUserToFollowList(user: String) {
        startActivity(UserListFollow.intent(this, user))
    }
    private fun moveUserToFollowingList(user: String) {
        startActivity(UserListFollowing.intent(this, user))
    }
    private fun moveUserToRepos(user: String) {
        startActivity(RepositoriesList.intent(this, user))
    }

    companion object {
        private const val KEY_USER_FOLLOW = "USER_DETAILS_FOLLOW"

        fun intent(context: Context, user: String): Intent {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra(KEY_USER_FOLLOW, user)
            return intent
        }
    }
}
