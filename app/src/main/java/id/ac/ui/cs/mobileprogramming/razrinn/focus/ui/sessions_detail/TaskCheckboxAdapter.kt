package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task

class TaskCheckboxAdapter(
    private val values: List<Task>,
    private val viewModel: TaskViewModel
) : RecyclerView.Adapter<TaskCheckboxAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task_checkbox_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val txt = item.name + " - " + intToPriority(item.priority)
        holder.taskName.text = txt
        holder.taskName.isChecked = item.isFinished
        holder.taskName.setOnCheckedChangeListener { buttonView, _ ->
            item.isFinished = buttonView.isChecked
            viewModel.update(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: CheckBox = view.findViewById(R.id.checkbox_task_item)
        override fun toString(): String {
            return super.toString() + " '" + taskName.text + "'"
        }
    }

    private fun intToPriority(int: Int): String {
        return when (int) {
            3 -> {
                "HIGH"
            }
            2 -> {
                "NORMAL"
            }
            else -> {
                "LOW"
            }
        }
    }
}