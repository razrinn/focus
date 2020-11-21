package id.ac.ui.cs.mobileprogramming.razrinn.focus.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import id.ac.ui.cs.mobileprogramming.razrinn.focus.network.APIConfig
import id.ac.ui.cs.mobileprogramming.razrinn.focus.network.model.Quote
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private var mHandler: Handler? = null

    // task to be run here
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            loadData()
            mHandler?.postDelayed(this, DEFAULT_SYNC_INTERVAL)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create the Handler object
        mHandler = Handler()
        // Execute a runnable task as soon as possible
        mHandler!!.post(runnableService)
        return START_STICKY
    }

    @Synchronized
    private fun loadData() {
        val rf = Retrofit.Builder()
            .baseUrl(APIConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val request = rf.create(APIConfig::class.java)
        val call = request.quote
        call?.enqueue(object:Callback<Quote?>{
            override fun onFailure(call: Call<Quote?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Failed to Load Quote Data. Please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Quote?>, response: Response<Quote?>) {
                val quote: Quote? = response.body() as Quote
                val author = quote?.author
                val content = quote?.content
                val intent = Intent(AppConstants.GET_QUOTE)
                intent.putExtra("author", author)
                intent.putExtra("content", content)
                val broadcastManager = LocalBroadcastManager.getInstance(applicationContext)
                broadcastManager.sendBroadcast(intent)
            }
        })
    }

    companion object{
        const val DEFAULT_SYNC_INTERVAL = 60 * 1000.toLong()
    }
}
