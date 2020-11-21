package id.ac.ui.cs.mobileprogramming.razrinn.focus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionUnfinished
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.AppConstants
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.NotificationUtil
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_STOP -> {
                SessionUnfinished.removeAlarm(context)
                PrefUtil.setTimerState(SessionUnfinished.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = SessionUnfinished.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                SessionUnfinished.removeAlarm(context)
                PrefUtil.setTimerState(SessionUnfinished.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = SessionUnfinished.setAlarm(context, SessionUnfinished.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(SessionUnfinished.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = SessionUnfinished.setAlarm(context, SessionUnfinished.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(SessionUnfinished.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
