package kryx07.intents2

import android.os.CountDownTimer
import mu.KotlinLogging
import java.util.concurrent.TimeUnit


/**
 * Created by sda on 28.06.17.
 */
class CountDown(val millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

    private val logger = KotlinLogging.logger {  }

    override fun onTick(millisUntilFinished: Long) {
        val ms = millisUntilFinished
        val text = String.format("%02d\' %02d\"",
                TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
                TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)))
        logger.error { text }

    }

    override fun onFinish() {
        logger.error { "dupa" }

    }
}