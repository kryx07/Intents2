package kryx07.intents2

import android.app.Application
import kryx07.intents2.di.AppComponent
import kryx07.intents2.di.AppModule
import kryx07.intents2.di.DaggerAppComponent

class App : Application() {

    var appComponent: AppComponent = null!!

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}