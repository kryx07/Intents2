package kryx07.intents2.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import kryx07.intents2.R
import kryx07.intents2.activities.SecondActivity
import kryx07.intents2.events.CountDownChangedTimeEvent
import kryx07.intents2.events.ServiceAlreadyRunningEvent
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class SampleService : Service() {

    var alarmManager: AlarmManager? = null

    fun setAlarm() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 123, intent, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager!!.setExact(AlarmManager.RTC, 2000, pendingIntent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        d("I'm in the service - onCreate")
        EventBus.getDefault().post(ServiceAlreadyRunningEvent())
        d("Posted that service is already running")

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        setAlarm()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //EventBus.getDefault().register(this)
        d("On Start Command???")
        val countDownTimer = CountDown(10000, 100)
        countDownTimer.start()
        return super.onStartCommand(intent, flags, startId)
    }


    fun broadcastAction(param: String) {
        val intent = Intent(getString(R.string.broadcast_action))
        intent.putExtra(getString(R.string.string), param)
        val bm = LocalBroadcastManager.getInstance(this)
        bm.sendBroadcast(intent)
    }

    fun sendString(str: String) {
        //d(str)
        broadcastAction(str)
        EventBus.getDefault().post(CountDownChangedTimeEvent(str))

    }

    inner class CountDown(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            val ms = millisUntilFinished
            val text = String.format("%02d\' %02d\"",
                    TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
                    TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)))
            sendString(text)

        }

        override fun onFinish() {
            sendString("Game Over")
            onDestroy()
        }
    }


    override fun onDestroy() {
//        EventBus.getDefault().register(this)
        d("Destroyed")
        super.onDestroy()

    }

    fun d(str: String) = Log.e(this.javaClass.simpleName, str)


}