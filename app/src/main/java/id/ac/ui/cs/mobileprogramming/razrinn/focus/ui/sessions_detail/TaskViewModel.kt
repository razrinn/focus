package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.repository.FocusRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: FocusRepository): ViewModel() {
    var tasks: LiveData<List<Task>>? = null
    fun setTasks(sessionId: Int) = viewModelScope.launch {
        tasks = repository.getTaskBySessionId(sessionId)
    }
    fun update(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }
}
class TaskViewModelFactory(private val repository: FocusRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}