package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.DetailSessionActivity
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R

import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.data.DetailSession.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SessionListAdapter(
    private val values: List<DummyItem>,
    private val viewModel: SessionViewModel
) : RecyclerView.Adapter<SessionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_session, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val isfinished: Boolean = item.id.toInt() % 2 == 0
        holder.contentView.text = item.content
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailSessionActivity::class.java)
            intent.putExtra("test_id", item.id)
            intent.putExtra("test_content", item.content)
            intent.putExtra("test_details", item.details)
            intent.putExtra("test_is_finished", isfinished)
            holder.itemView.context.startActivity(intent)
//            viewModel.setSession(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}