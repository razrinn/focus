package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.razrinn.focus.DetailSessionActivity
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [SessionFinished.newInstance] factory method to
 * create an instance of this fragment.
 */
class SessionFinished : Fragment() {
    private lateinit var viewModel: SessionDetailViewModel
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity!!).get(SessionDetailViewModel::class.java)
        taskViewModel = ViewModelProvider(activity!!).get(TaskViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_session_finished, container, false)
        viewModel.session?.observe(viewLifecycleOwner,
            Observer<SessionWithTasks> {
                val sessionName:TextView = view.findViewById(R.id.session_goal_finished)
                val sessionTime:TextView = view.findViewById(R.id.session_detail_time)
                val sessionRating:TextView = view.findViewById(R.id.session_detail_rating)
                val sessionReview:TextView = view.findViewById(R.id.session_detail_review)
                sessionName.text = it.session.goal
                sessionTime.text = it.session.totalTime.toString()
                sessionRating.text = it.session.rating.toString()
                sessionReview.text = it.session.review
            })
        val recyclerView = view.findViewById<RecyclerView>(R.id.list_task)
        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context)
                activity?.let {act ->
                    taskViewModel.tasks?.observe(act, Observer {tasks->
                        val noTaskView: TextView = view.findViewById(R.id.no_task_yet_unf)
                        if(tasks.isEmpty()) noTaskView.visibility = View.VISIBLE
                        else noTaskView.visibility = View.GONE
                        adapter = TaskListAdapter(tasks, activity!! as DetailSessionActivity)
                    })
                }
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SessionFinished().apply {
                arguments = Bundle().apply {

                }
            }
    }
}