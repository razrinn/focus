package id.ac.ui.cs.mobileprogramming.razrinn.focus

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.application.FocusApplication
import id.ac.ui.cs.mobileprogramming.razrinn.focus.service.QuoteService
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModel
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModelFactory
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_dialog.CreateSessionDialog
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.AppConstants
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.PrefUtil


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory((application as FocusApplication).focusRepository)
    }
    private var quoteReceiver: BroadcastReceiver? = null
    private var imageView: ImageView? = null
    private var profilePhoto: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startQuoteService()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        quoteReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action.toString() == AppConstants.GET_QUOTE) {
                    val author = intent.getStringExtra("author")
                    val content = intent.getStringExtra("content")
                    val tv1: TextView = findViewById(R.id.quote_content)
                    val tv2: TextView = findViewById(R.id.quote_author)
                    tv1.text = content
                    tv2.text = author
                }
            }
        }
        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(
                quoteReceiver as BroadcastReceiver,
                IntentFilter(AppConstants.GET_QUOTE)
            )
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val dialog = CreateSessionDialog()
            dialog.show(fragmentManager, "create_session")
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_session, R.id.nav_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val nv: NavigationView = findViewById(R.id.nav_view)
        val hv: View = nv.getHeaderView(0)
        val fullName: TextView = hv.findViewById(R.id.full_name)
        val jobTitle: TextView = hv.findViewById(R.id.job_title)
        imageView = hv.findViewById(R.id.imageView)
        val txt1 = preferences.getString("first_name", "John") + " " + preferences.getString("last_name", "Doe")
        fullName.text = txt1
        jobTitle.text = preferences.getString("job_title", "Example Job Title")
        val profilePicture = PrefUtil.getProfilePicture(this)
        if(profilePicture != ""){
            imageView?.setImageURI(Uri.parse(profilePicture))
        }
        imageView?.setOnClickListener {
            selectImage()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun selectImage() {
        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) ==  PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(
                Intent.ACTION_OPEN_DOCUMENT,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                return
            }
            val selectedImage: Uri? = data?.data
            profilePhoto = selectedImage.toString()
            PrefUtil.setProfilePicture(profilePhoto, this)
            imageView?.setImageURI(selectedImage)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage()
                }
            }
        }
    }

    private fun startQuoteService(){
        Intent(this, QuoteService::class.java).also { intent ->
            startService(intent)
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1000
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    }
}