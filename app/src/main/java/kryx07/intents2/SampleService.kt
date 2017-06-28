package kryx07.intents2

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import java.util.concurrent.TimeUnit


/**
 * Created by sda on 28.06.17.
 */
class SampleService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        d("I'm in the service - onCreate")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
        d(str)
        broadcastAction(str)

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

        d("Destroyed")
        super.onDestroy()

    }

    fun d(str: String) = Log.e(this.javaClass.simpleName, str)


}