package id.ac.ui.cs.mobileprogramming.razrinn.focus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionUnfinished
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.NotificationUtil
import id.ac.ui.cs.mobileprogramming.razrinn.focus.util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(SessionUnfinished.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
