package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.data.DetailSession

class SessionViewModel : ViewModel() {

    private val session = MutableLiveData<DetailSession.DummyItem>()

    fun setSession(newValue:DetailSession.DummyItem){
        session.value = newValue
    }

    fun getSession(): MutableLiveData<DetailSession.DummyItem> {
        return session
    }
}