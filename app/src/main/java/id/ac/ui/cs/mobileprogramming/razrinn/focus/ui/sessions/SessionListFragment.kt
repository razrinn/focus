package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import id.ac.ui.cs.mobileprogramming.razrinn.focus.MainActivity
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.application.FocusApplication
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category

/**
 * A fragment representing a list of Items.
 */
class SessionListFragment : Fragment() {

    private var columnCount = 1
    private val viewModel: SessionViewModel by viewModels {
        SessionViewModelFactory((activity?.application as FocusApplication).focusRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_session_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                activity?.let {act ->
                    viewModel.allSessions.observe(act, Observer {sessions->
                        viewModel.allCategories.observe(act, Observer <List<Category>>{
                            adapter = SessionListAdapter(sessions, activity!! as MainActivity, it)
                        })
                    })
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SessionListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}