package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R

class CreateSessionDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_create_session, null))
                // Add action buttons
                .setTitle(R.string.create_session)
                .setPositiveButton(R.string.create
                ) { dialog, id ->
                    // sign in the user ...
                }
                .setNegativeButton(R.string.cancel
                ) { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}