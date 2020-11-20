package id.ac.ui.cs.mobileprogramming.razrinn.focus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.razrinn.focus.application.FocusApplication
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModel
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModelFactory
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.*

class DetailSessionActivity : AppCompatActivity() {
    private val viewModel: SessionDetailViewModel by viewModels {
        SessionDetailViewModelFactory((application as FocusApplication).focusRepository)
    }
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as FocusApplication).focusRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFinished = intent.getBooleanExtra("session_is_finished", false)
        val sessionId = intent.getIntExtra("session_id", 0)
        setContentView(R.layout.activity_detail_session)
        viewModel.setSession(sessionId)
        taskViewModel.setTasks(sessionId)
        val newFragment = if (isFinished) SessionFinished() else SessionUnfinished()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_detail_session, newFragment)
            .commit()
        setTitle(R.string.session)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return false
    }
}