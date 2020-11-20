package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.DetailSessionActivity
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task

class TaskListAdapter(
    private val values: List<Task>,
    private val mainActivity: DetailSessionActivity
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.taskName.text = item.name
        holder.taskIsFinished.text = if (item.isFinished) "Finished" else "Unfinished"
        val isFinishedColor =
            if (item.isFinished) mainActivity.getColor(R.color.colorSuccess)
            else mainActivity.getColor(R.color.colorDanger)
        holder.taskIsFinished.setTextColor(isFinishedColor)
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailSessionActivity::class.java)
//            intent.putExtra("session_id", item.id)
//            intent.putExtra("session_is_finished", item.isFinished)
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.task_list_name)
        val taskIsFinished: TextView = view.findViewById(R.id.task_list_is_finished)
        override fun toString(): String {
            return super.toString() + " '" + taskName.text + "'"
        }
    }
}