package kryx07.intents2.di

import kryx07.intents2.IntentActivity
import javax.inject.Singleton

/**
 * Created by sda on 27.06.17.
 */
@Singleton
@dagger.Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(intentActivity: IntentActivity)
}