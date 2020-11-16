package id.ac.ui.cs.mobileprogramming.razrinn.focus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView

class DetailSessionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFinished = intent.getBooleanExtra("test_is_finished", false)
        if (isFinished) setContentView(R.layout.activity_finished_session)
        else setContentView(R.layout.activity_unfinished_session)
        setTitle(R.string.session)

        val testText = findViewById<TextView>(R.id.test)
        testText?.text = intent.getStringExtra("test_content")
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