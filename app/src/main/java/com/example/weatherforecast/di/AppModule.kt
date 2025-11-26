package com.example.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecast.data.local.Converters
import com.example.weatherforecast.data.local.WeatherDao
import com.example.weatherforecast.data.local.WeatherDatabase
import com.example.weatherforecast.data.network.WeatherApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

// アプリ全体にシングルトンとして提供
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.openweathermap.org/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        // JSONをKotlinデータクラスに変換
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // APIの通信内容をログに出力
        val logging = HttpLoggingInterceptor()
            .setLevel(
                level = HttpLoggingInterceptor.Level.BODY
            )
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            // Jsonを変換するコンバーターにMoshiを使用
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL) // ウェブサービスのベースURLを追加
            // 生成したOkHttpClientをセット
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherService(
        retrofit: Retrofit
    ): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context,
        converters: Converters
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = WeatherDatabase::class.java,
            name = "weather_forecast_database"
        )
            .addTypeConverter(converters)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideWeatherDao(
        db: WeatherDatabase
    ): WeatherDao = db.WeatherDao()
}
