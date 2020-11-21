package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import androidx.lifecycle.*
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.FocusRepository
import kotlinx.coroutines.launch

class SessionDetailViewModel(private val repository: FocusRepository): ViewModel() {
    var session: LiveData<SessionWithTasks>? = null
    var counter: MutableLiveData<Int> = MutableLiveData(0)
    fun setSession(sessionId: Int) = viewModelScope.launch {
        session = repository.getSession(sessionId)
    }
    fun update(session: Session) = viewModelScope.launch {
        repository.updateSession(session)
    }
    fun addTask(task: Task) = viewModelScope.launch{
        repository.insertTask(task)
    }
    fun addCounter() {
        counter.value = counter.value?.plus(1)
    }
    fun resetCounter(){
        counter.value = 0
    }
}
class SessionDetailViewModelFactory(private val repository: FocusRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SessionDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}