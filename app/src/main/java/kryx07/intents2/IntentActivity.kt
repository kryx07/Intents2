package kryx07.intents2

import android.content.*
import android.content.Context.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_main.*
import mu.KotlinLogging


class IntentActivity : AppCompatActivity() {
    private val CAMERA_REQUEST = 1888
    private val TEXT_REQUEST = 1889

    private val logger = KotlinLogging.logger {}

    private var myServiceBinder: SampleServiceBound.MyBinder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        doBindService()
    }


    override fun onStart() {
        super.onStart()
        registerReceiver()
    }

    @OnClick(R.id.call_button)
    fun call() {
        call_button.setOnClickListener { }
        if (phone_number == null) {
            return
        }
        if (phone_number.text.isNotEmpty()) {
            val phoneNumber = "tel:" + this.phone_number.text.toString()
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse(phoneNumber)
            startActivity(i)
        }
    }

    @OnClick(R.id.camera_button)
    fun takeAPhoto() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    @OnClick(R.id.second_activity_button)
    fun startSecondActivityForTextResult() = startActivityForResult(Intent(this, SecondActivity::class.java), TEXT_REQUEST)

    @OnClick(R.id.service_start_button)
    fun startCountDownService() {
        d("Clicked Start Service")
        applicationContext.startService(Intent(applicationContext, SampleService::class.java))
        //applicationContext.startService(Intent(this@IntentActivity, SampleServiceBound::class.java))
    }

    @OnClick(R.id.service_start_button2)
    fun refreshDataFromBoundService() {
        d("Clicked Start Service")
        //applicationContext.startService(Intent(this@IntentActivity, SampleServiceBound::class.java))
        if (myService != null) {
            d("myService is initialized")
            d("time received: " + myService!!.showTime())
            countdown2.text = myService!!.showTime()

        } else {
            d("myService is null")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            CAMERA_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    val photo = data.extras.get("data") as Bitmap
                    image.setImageBitmap(photo)
                } else {
                    logger.debug { "Failure to complete: " + "CAMERA_REQUEST" }
                }
            }
            TEXT_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    second_activity_result.text = """${getString(R.string.second_activity_says)}${" "}${data.getStringExtra("text")}"""
                } else {
                    logger.debug { "Failure to complete: " + "TEXT_REQUEST" }
                }
            }
        }

    }

    fun registerReceiver() {
        val loadingReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == getString(R.string.broadcast_action)) {
                    val param = intent.getStringExtra(getString(R.string.string))
                    countdown.text = param
                }
            }
        }
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(loadingReceiver, IntentFilter(getString(R.string.broadcast_action)));
    }

    fun d(str: String) = Log.e(this.javaClass.simpleName, str)

    private var myService: SampleServiceBound? = null
    var mServiceConnection: ServiceConnection = object : ServiceConnection {


        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            Log.d("ServiceConnection", "connected")
            myServiceBinder = binder as SampleServiceBound.MyBinder?
            if (myServiceBinder != null) {
                myService = (myServiceBinder as SampleServiceBound.MyBinder).getService()
            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("ServiceConnection", "disconnected")
            myService = null

        }
    }

    fun doBindService() {
        this.bindService(Intent(this, SampleServiceBound::class.java), mServiceConnection, Context.BIND_AUTO_CREATE)
    }

}
