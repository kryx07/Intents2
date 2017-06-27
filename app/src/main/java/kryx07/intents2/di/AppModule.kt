package kryx07.intents2.di

import android.content.Context
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sda on 27.06.17.
 */
@dagger.Module
class AppModule(var context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

}