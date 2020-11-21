package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R
import id.ac.ui.cs.mobileprogramming.razrinn.focus.database.entity.SessionWithTasks
import id.ac.ui.cs.mobileprogramming.razrinn.focus.receiver.TimerExpiredReceiver
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_dialog.FinishSessionDialog
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.task_dialog.CreateTaskDialog
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.NotificationUtil
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.PrefUtil
import kotlinx.android.synthetic.main.fragment_session_unfinished.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [SessionUnfinished.newInstance] factory method to
 * create an instance of this fragment.
 */
class SessionUnfinished : Fragment() {
    enum class TimerState {
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthInSeconds: Long = 0
    private var timerState = TimerState.Stopped
    private var timerRemainginInSeconds: Long = 0
    private lateinit var viewModel: SessionDetailViewModel
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onResume() {
        super.onResume()

        initTimer()
        NotificationUtil.hideTimerNotification(activity!!)
    }

    override fun onPause() {
        super.onPause()
        if (timerState == TimerState.Running) {
            timer.cancel()
            val wakeUpTime = setAlarm(activity!!, nowSeconds, timerRemainginInSeconds)
            NotificationUtil.showTimerRunning(activity!!, wakeUpTime)
        } else if (timerState == TimerState.Paused) {
            NotificationUtil.showTimerPaused(activity!!)
        }
        activity?.let { PrefUtil.setPreviousTimerLengthSeconds(timerLengthInSeconds, it) }
        activity?.let { PrefUtil.setSecondsRemaining(timerRemainginInSeconds, it) }
        activity?.let { PrefUtil.setTimerState(timerState, it) }
    }

    private fun initTimer() {
        timerState = activity?.let { PrefUtil.getTimerState(it) }!!
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        timerRemainginInSeconds =
            if (timerState == TimerState.Running || timerState == TimerState.Paused)
                PrefUtil.getSecondsRemaining(activity!!)
            else
                timerLengthInSeconds
        val alarmSetTime = PrefUtil.getAlarmSetTime(activity!!)
        if (alarmSetTime > 0)
            timerRemainginInSeconds -= nowSeconds - alarmSetTime

        if (timerRemainginInSeconds <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        //set the length of the timer to be the one set in SettingsActivity
        //if the length was changed when the timer was running
        setNewTimerLength()

        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthInSeconds, activity!!)
        timerRemainginInSeconds = timerLengthInSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer() {
        viewModel = ViewModelProvider(activity!!).get(SessionDetailViewModel::class.java)
        timerState = TimerState.Running
        timer = object : CountDownTimer(timerRemainginInSeconds * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                viewModel.addCounter()
                timerRemainginInSeconds = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLength(activity!!)
        timerLengthInSeconds = (lengthInMinutes * 60L)
        progress_countdown.max = timerLengthInSeconds.toInt()
    }

    private fun setPreviousTimerLength() {
        timerLengthInSeconds = PrefUtil.getPreviousTimerLengthSeconds(activity!!)
        progress_countdown.max = timerLengthInSeconds.toInt()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = timerRemainginInSeconds / 60
        val secondsInMinuteUntilFinished = timerRemainginInSeconds - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        timer_countdown.text =
            "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        progress_countdown.progress = (timerLengthInSeconds - timerRemainginInSeconds).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                play_btn.isEnabled = false
                play_btn.setBackgroundColor(Color.GRAY)
                pause_btn.isEnabled = true
            }
            TimerState.Stopped -> {
                play_btn.isEnabled = true
                pause_btn.isEnabled = false
                pause_btn.setBackgroundColor(Color.GRAY)
            }
            TimerState.Paused -> {
                play_btn.isEnabled = true
                pause_btn.isEnabled = false
                pause_btn.setBackgroundColor(Color.GRAY)
            }
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
        val pomodoroBtn: Button = view.findViewById(R.id.select_pomodoro_btn)
        pomodoroBtn.setOnClickListener {
            PrefUtil.setTimerLength(25, activity!!)
            timer.cancel()
            onTimerFinished()
        }
        val longBtn: Button = view.findViewById(R.id.select_long_btn)
        longBtn.setOnClickListener {
            PrefUtil.setTimerLength(10, activity!!)
            timer.cancel()
            onTimerFinished()
        }
        val shortBtn: Button = view.findViewById(R.id.select_short_btn)
        shortBtn.setOnClickListener {
            PrefUtil.setTimerLength(5, activity!!)
            timer.cancel()
            onTimerFinished()
        }
        val playBtn = view.findViewById<FloatingActionButton>(R.id.play_btn)
        playBtn.setOnClickListener {
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }
        val pauseBtn = view.findViewById<FloatingActionButton>(R.id.pause_btn)
        pauseBtn.setOnClickListener {
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
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
                activity?.let { act ->
                    taskViewModel.tasks?.observe(act, Observer { tasks ->
                        val noTaskView: TextView = view.findViewById(R.id.no_task_yet)
                        if (tasks.isEmpty()) noTaskView.visibility = View.VISIBLE
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

        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }
}