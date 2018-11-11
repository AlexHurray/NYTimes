package com.example.ermolaenkoalex.nytimes.api;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestApi {

    private static final String URL = "http://api.nytimes.com/";
    private static final String API_KEY = "dec3395ed3034d1599bac75404d47ed2";
    private static final int TIMEOUT_IN_SECONDS = 2;

    private static NewsEndpoint newsEndpoint;

    public static synchronized NewsEndpoint news() {
        if (newsEndpoint == null) {
            newsEndpoint = createNewsEndpoint();
        }
        return newsEndpoint;
    }

    private RestApi() {
        throw new AssertionError("No instances");
    }

    @NonNull
    private static Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new EnumValueConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(networkLogInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    private static NewsEndpoint createNewsEndpoint() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        return retrofit.create(NewsEndpoint.class);
    }
}
