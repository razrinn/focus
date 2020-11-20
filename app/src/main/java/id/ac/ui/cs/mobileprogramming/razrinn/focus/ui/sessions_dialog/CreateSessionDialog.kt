package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.application.FocusApplication
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Category
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.Session
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModel
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions.SessionViewModelFactory

class CreateSessionDialog : DialogFragment() {
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory((activity?.application as FocusApplication).focusRepository)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_create_session, null)
            // Inflate and set the layout for the dialog
            val spinner: Spinner = view.findViewById(R.id.field_session_category)
            val spinnerAdapter = ArrayAdapter<Category>(activity!!, android.R.layout.simple_spinner_item, ArrayList())
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter
            sessionViewModel.allCategories.observe(this, Observer {categories ->
                spinnerAdapter.addAll(categories)
            })
            spinnerAdapter.notifyDataSetChanged()
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setTitle(R.string.create_session)
                .setPositiveButton(R.string.create
                ) { dialog, id ->
                    val d: Dialog = dialog as Dialog
                    val editText: EditText = d.findViewById(R.id.field_session_name)
                    val categorySpinner: Spinner = d.findViewById(R.id.field_session_category)
                    val selected: Category = categorySpinner.selectedItem as Category
                    val newSession = Session(goal = editText.text.toString(), categoryId = selected.id)
                    sessionViewModel.insert(newSession)
                }
                .setNegativeButton(R.string.cancel
                ) { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}