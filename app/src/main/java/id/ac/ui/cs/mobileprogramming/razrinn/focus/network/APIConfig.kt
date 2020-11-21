package id.ac.ui.cs.mobileprogramming.razrinn.focus.network

import id.ac.ui.cs.mobileprogramming.razrinn.focus.network.model.Quote
import retrofit2.Call
import retrofit2.http.GET

interface APIConfig {

    @get:GET("random")
    val quote: Call<Quote>?
    companion object {
        const val BASE_URL = "https://api.quotable.io"
    }
}