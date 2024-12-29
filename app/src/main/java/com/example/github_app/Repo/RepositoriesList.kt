package com.example.github_app.Repo

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
import com.example.github_app.Commen.Repositories
import com.example.github_app.R
import com.example.github_app.databinding.ActivityRepositoriesListBinding
import kotlinx.coroutines.launch

class RepositoriesList : AppCompatActivity() {
    private lateinit var binding: ActivityRepositoriesListBinding
    private val viewModel: RepoViewModel by viewModels { RepoViewModel.factory(userName()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories_list)
        binding = ActivityRepositoriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observerState()
        observerStateLang()
        configureToolBar()
    }
    private fun configureToolBar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            this.title = "Repositories"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun observerStateLang() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.stateLang.collect {
                    when (it) {
                        RepoLangState.Failure -> {}
                        RepoLangState.Loading -> {}
                        is RepoLangState.Success ->
                            showRepositoriesLang(it.repoLangDetails)
                    }
                }
            }
        }
    }

    private fun observerState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        UserRepoState.Loading -> {}
                        is UserRepoState.Success -> {
                            showRepositories(it.userRepoDetails)
                        }
                        UserRepoState.Failure -> {}
                    }
                }
            }
        }
    }

    private fun userName(): String {
        return intent.getStringExtra(KEY_REPO)!!
    }
    private fun showRepositoriesLang(repo: List<Repositories>) {
        binding.recyclerView.apply {
            visibility = View.VISIBLE
            adapter = RepositoryViewHolderAdapter(item = repo)
            addItemDecoration(DividerItemDecoration(this@RepositoriesList, LinearLayout.VERTICAL))
            layoutManager = LinearLayoutManager(this@RepositoriesList)
            setHasFixedSize(true)
        }
    }

    private fun showRepositories(repo: List<Repositories>) {
        binding.recyclerView.apply {
            visibility = View.VISIBLE
            adapter = RepositoryViewHolderAdapter(item = repo)
            addItemDecoration(DividerItemDecoration(this@RepositoriesList, LinearLayout.VERTICAL))
            layoutManager = LinearLayoutManager(this@RepositoriesList)
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val KEY_REPO = "USER_REPO"

        fun intent(context: Context, userName: String): Intent {
            val intent = Intent(context, RepositoriesList::class.java)
            intent.putExtra(KEY_REPO, userName)
            return intent
        }
    }
}
