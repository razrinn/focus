package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.DetailSessionActivity
import id.ac.ui.cs.mobileprogramming.razrinn.focus.MainActivity
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks

class TaskListAdapter(
    private val values: List<SessionWithTasks>,
    private val mainActivity: MainActivity
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_session, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position].session
        Log.d("DEBUG", item.createdAt.toString())
        holder.sessionName.text = item.goal
        holder.sessionDate.text = item.createdAt.toString()
        holder.sessionIsFinished.text = if (item.isFinished) "Finished" else "Ongoing"
        val isFinishedColor =
            if (item.isFinished) mainActivity.getColor(R.color.colorSuccess)
            else mainActivity.getColor(R.color.colorPrimary)
        holder.sessionIsFinished.setTextColor(isFinishedColor)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailSessionActivity::class.java)
            intent.putExtra("session_id", item.id)
            Log.d("DEBUG_ADAPTER", item.id.toString())
            intent.putExtra("session_is_finished", item.isFinished)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sessionName: TextView = view.findViewById(R.id.session_list_name)
        val sessionDate: TextView = view.findViewById(R.id.session_list_date)
        val sessionIsFinished: TextView = view.findViewById(R.id.session_list_is_finished)
        override fun toString(): String {
            return super.toString() + " '" + sessionName.text + "'"
        }
    }
}