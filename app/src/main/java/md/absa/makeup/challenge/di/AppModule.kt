package md.absa.makeup.challenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import md.absa.makeup.challenge.MakeUpApplication
import md.absa.makeup.challenge.data.db.MakeUpDatabase
import md.absa.makeup.challenge.data.api.MyHttpClient
import md.absa.makeup.challenge.data.api.RetrofitInstance
import md.absa.makeup.challenge.data.api.RetrofitInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MakeUpApplication {
        return app as MakeUpApplication
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(retrofitInstance: RetrofitInstance): RetrofitInterface {
        return retrofitInstance.retrofitInterface
    }

    @Singleton
    @Provides
    fun provideMyHttpClient(@ApplicationContext context: Context): MyHttpClient {
        return MyHttpClient(context)
    }

    @Singleton
    @Provides
    fun provideMakeUpDatabase(@ApplicationContext context: Context): MakeUpDatabase {
        return MakeUpDatabase.getInstance(context)
    }
}
