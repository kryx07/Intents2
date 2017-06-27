package kryx07.intents2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_main.*


class IntentActivity : AppCompatActivity() {
    private val CAMERA_REQUEST = 1888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        init()
    }

    fun init() {
        /* call_button.setOnClickListener { call() }
         camera_button.setOnClickListener { takeAPhoto() }*/
        //call_button.setOnClickListener { call() }
        /*val url = "http://www.google.com"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)*/
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data.extras.get("data") as Bitmap
            image.setImageBitmap(photo)
        }
    }
}
