package id.ac.ui.cs.mobileprogramming.razrinn.focus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionFinished
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionUnfinished

class DetailSessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFinished = intent.getBooleanExtra("test_is_finished", false)
        setContentView(R.layout.activity_detail_session)
        val newFragment = if (isFinished) SessionFinished() else SessionUnfinished()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_detail_session, newFragment)
            .commit()
        setTitle(R.string.session)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return false
    }
}