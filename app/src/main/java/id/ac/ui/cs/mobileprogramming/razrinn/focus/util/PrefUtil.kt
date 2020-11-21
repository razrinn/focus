package id.ac.ui.cs.mobileprogramming.razrinn.focus.util

import android.content.Context
import androidx.preference.PreferenceManager
import id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.sessions_detail.SessionUnfinished

class PrefUtil {
    companion object {
        private const val TIMER_LENGTH_MINUTES_ID = "id.ac.ui.cs.mobileprogramming.razrinn.focus.timer_length"
        fun getTimerLength(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(TIMER_LENGTH_MINUTES_ID, 15)
        }
        fun setTimerLength(minutes: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(TIMER_LENGTH_MINUTES_ID, minutes)
            editor.apply()
        }
        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "id.ac.ui.cs.mobileprogramming.razrinn.focus.previous_timer_length"
        fun getPreviousTimerLengthSeconds(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }


        private const val TIMER_STATE_ID = "id.ac.ui.cs.mobileprogramming.razrinn.focus.timer_state"

        fun getTimerState(context: Context): SessionUnfinished.TimerState{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return SessionUnfinished.TimerState.values()[ordinal]
        }

        fun setTimerState(state: SessionUnfinished.TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }


        private const val SECONDS_REMAINING_ID = "id.ac.ui.cs.mobileprogramming.razrinn.focus.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

        private const val ALARM_SET_TIME_ID = "id.ac.ui.cs.mobileprogramming.razrinn.focus.backgrounded_time"

        fun getAlarmSetTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(ALARM_SET_TIME_ID, 0)
        }

        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }

        private const val PROFILE_PICTURE_PATH_ID = "id.ac.ui.cs.mobileprogramming.razrinn.focus.profile_picture"

        fun getProfilePicture(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getString(PROFILE_PICTURE_PATH_ID, "")
        }

        fun setProfilePicture(path: String, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(PROFILE_PICTURE_PATH_ID, path)
            editor.apply()
        }
    }
}