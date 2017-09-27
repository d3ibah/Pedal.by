package by.data.internet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.data.entity.ProfileData;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home911 on 21.09.2017.
 */

public class RestService {

    public static final String BASE_URL = "https://pedal.by/api/v1/adverts/part/filter/";

    private static final RestService instance = new RestService();

    private RestApi restApi;

    private RestService(){
        init();
    }

    public static RestService getInstance() {
        return instance;
    }

    private void init() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Gson gson =  new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public Observable<List<ProfileData>> getProfiles() {
        return restApi.getProfiles();
    }

    public Observable<ProfileData> getProfile(String id) {
        return restApi.getProfile(id);
    }

    public Observable<Void> saveProfile(ProfileData profile) {
        return restApi.saveProfile(profile);
    }

    /*public Observable<ProfileData> overwriteProfile(ProfileData profile) {
        return restApi.overwriteProfile(profile, profile.getId());
    }*/
}
