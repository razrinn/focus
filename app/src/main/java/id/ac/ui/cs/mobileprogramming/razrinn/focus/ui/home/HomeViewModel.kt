package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.FocusRepository
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.UserRepository

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}