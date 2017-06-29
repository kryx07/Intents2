package kryx07.intents2.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_second.*
import kryx07.intents2.R


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        ButterKnife.bind(this)
        init()
    }

    fun init() {

    }



    @OnClick(R.id.return_button)
    fun returnTextToBaseActivity() {
        setResult(Activity.RESULT_OK, Intent().putExtra("text", text_result_input.text.toString()))
        finish()
    }


}
