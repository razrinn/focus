package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionDetailViewModel

class FinishSessionDialog(private val viewModel: SessionDetailViewModel) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_finish_session_dialog, null)
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setTitle(R.string.finish_session)
                .setMessage(R.string._finish_session_confirmation)
                .setPositiveButton(R.string.confirm
                ) { dialog, id ->
                    val d: Dialog = dialog as Dialog
                    val reviewView: TextView = d.findViewById(R.id.field_session_review)
                    val ratingView: TextView = d.findViewById(R.id.field_session_rating)
                    viewModel.session?.observe(this, Observer {st ->
                        val s = st.session
                        s.review = reviewView.text.toString()
                        s.rating = ratingView.text.toString().toInt()
                        s.isFinished = true
                        viewModel.update(s)
                    })
                    activity!!.finish()
                }
                .setNegativeButton(R.string.cancel
                ) { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}