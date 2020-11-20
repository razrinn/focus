package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.task_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.application.FocusApplication
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Task
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModel
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModelFactory
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionDetailViewModel

class CreateTaskDialog(private val viewModel: SessionDetailViewModel) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_create_task_dialog, null)
            // Inflate and set the layout for the dialog
            val spinner: Spinner = view.findViewById(R.id.field_task_priority)
            val options: ArrayList<String> = arrayListOf("HIGH", "NORMAL", "LOW")
            val spinnerAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, options)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setTitle(R.string.create_task)
                .setPositiveButton(R.string.create
                ) { dialog, id ->
                    val d: Dialog = dialog as Dialog
                    val editText: EditText = d.findViewById(R.id.field_task_name)
                    val name: String = editText.text.toString()
                    val prioritySpinner: Spinner = d.findViewById(R.id.field_task_priority)
                    val selected: String = prioritySpinner.selectedItem.toString()
                    var sessionId = 0
                    viewModel.session?.observe(activity!!, Observer {st ->
                        sessionId = st.session.id
                    })
                    val newTask = Task(
                        name = name,
                        priority = priorityToInt(selected),
                        sessionId = sessionId
                    )
                    viewModel.addTask(newTask)
                }
                .setNegativeButton(R.string.cancel
                ) { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun priorityToInt(string: String): Int {
        return when (string) {
            "HIGH" -> {
                3
            }
            "NORMAL" -> {
                2
            }
            else -> {
                1
            }
        }
    }
}