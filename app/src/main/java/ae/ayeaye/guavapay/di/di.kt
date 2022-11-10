package ae.ayeaye.guavapay.di

import ae.ayeaye.guavapay.MyApplication
import ae.ayeaye.guavapay.db.AppDatabase
import ae.ayeaye.guavapay.domain.RestApi
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Di2Module {

    @Singleton
    @Provides
    fun providesRestService(): RestApi  {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "database"
        ).build()
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class DiModule {


}