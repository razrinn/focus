package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_dialog.FinishSessionDialog
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.task_dialog.CreateTaskDialog


/**
 * A simple [Fragment] subclass.
 * Use the [SessionUnfinished.newInstance] factory method to
 * create an instance of this fragment.
 */
class SessionUnfinished : Fragment() {
    // TODO: Rename and change types of parameters
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
        val fragmentManager = activity?.supportFragmentManager
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(activity!!).get(SessionDetailViewModel::class.java)
        taskViewModel = ViewModelProvider(activity!!).get(TaskViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_session_unfinished, container, false)
        val playPauseBtn = view.findViewById<FloatingActionButton>(R.id.play_pause_btn)
        playPauseBtn.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.container_detail_session),
                "Look at me, I'm a fancy play/pause", Snackbar.LENGTH_LONG
            ).show()
        }
        val finishSessionBtn = view.findViewById<Button>(R.id.finish_session_btn)
        finishSessionBtn.setOnClickListener {
            val dialog = FinishSessionDialog(viewModel)
            if (fragmentManager != null) {
                dialog.show(fragmentManager, "finish_session")
            }
        }
        val addNewTaskBtn = view.findViewById<TextView>(R.id.add_new_task_btn)
        addNewTaskBtn.setOnClickListener {
            val dialog = CreateTaskDialog(viewModel)
            if (fragmentManager != null) {
                dialog.show(fragmentManager, "create_task")
            }
        }
        viewModel.session?.observe(viewLifecycleOwner,
            Observer<SessionWithTasks> {
                val sessionGoalText = view.findViewById<TextView>(R.id.session_goal_unfinished)
                sessionGoalText.text = it.session.goal
            })
        val recyclerView = view.findViewById<RecyclerView>(R.id.checkbox_task)
        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context)
                activity?.let {act ->
                    taskViewModel.tasks?.observe(act, Observer {tasks->
                        val noTaskView: TextView = view.findViewById(R.id.no_task_yet)
                        if(tasks.isEmpty()) noTaskView.visibility = View.VISIBLE
                        else noTaskView.visibility = View.GONE
                        adapter = TaskCheckboxAdapter(tasks, taskViewModel)
                    })
                }
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SessionUnfinished().apply {
                arguments = Bundle().apply {

                }
            }
    }
}