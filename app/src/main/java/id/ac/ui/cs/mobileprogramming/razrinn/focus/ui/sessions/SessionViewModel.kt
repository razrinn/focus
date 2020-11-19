package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.FocusRepository
import kotlinx.coroutines.launch

class SessionViewModel(private val repository: FocusRepository): ViewModel() {
    val allSessions: LiveData<List<SessionWithTasks>> = repository.allSessions
}
class SessionViewModelFactory(private val repository: FocusRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}