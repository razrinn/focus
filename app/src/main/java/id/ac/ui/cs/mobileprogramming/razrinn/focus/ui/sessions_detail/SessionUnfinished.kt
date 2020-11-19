package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks


/**
 * A simple [Fragment] subclass.
 * Use the [SessionUnfinished.newInstance] factory method to
 * create an instance of this fragment.
 */
class SessionUnfinished : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel: SessionDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(activity!!).get(SessionDetailViewModel::class.java)
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
            Snackbar.make(
                activity!!.findViewById(R.id.container_detail_session),
                "Look at me, I'm a fancy finish session", Snackbar.LENGTH_LONG
            ).show()
        }
        val addNewTaskBtn = view.findViewById<TextView>(R.id.add_new_task_btn)
        addNewTaskBtn.setOnClickListener {
            Snackbar.make(
                activity!!.findViewById(R.id.container_detail_session),
                "Look at me, I'm a fancy add new task", Snackbar.LENGTH_LONG
            ).show()
        }
        viewModel.session?.observe(viewLifecycleOwner,
            Observer<SessionWithTasks> {
                val sessionGoalText = view.findViewById<TextView>(R.id.session_goal_unfinished)
                sessionGoalText.text = it.session.goal
            })
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