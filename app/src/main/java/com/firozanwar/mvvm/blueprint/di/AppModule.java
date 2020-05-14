package com.firozanwar.mvvm.blueprint.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.firozanwar.mvvm.blueprint.R;
import com.firozanwar.mvvm.blueprint.model.User;
import com.firozanwar.mvvm.blueprint.utils.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    // Using this we can directly get the string  @Inject String myString in AuthActivity;
    @Provides
    static String someString() {
        return "Hi this string is getting injected from AppModule";
    }

    /**
     * Using this we can directly get the boolean  @Inject boolean isApp in AuthActivity;
     * <p>
     * It will return false because (Application application) already build in AppComponent's as a @BindsInstance.
     * So it will be available to all modules inserted in AppComponent.
     */
    @Provides
    static boolean getApp(Application application) {
        return application == null;
    }

    /************ Glide library injection - start ************/

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    /**
     * Here (Application application) will available through AppComponent's as a @BindsInstance and
     * (RequestOptions requestOptions) is available because it is defined in same module above.
     */
    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide
                .with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    /**
     * * Here (Application application) will available through AppComponent's as a @BindsInstance
     */
    @Singleton
    @Provides
    static Drawable provideLogoDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.logo);
    }

    /************ Glide library injection - end ************/

    /************ Retrofit library injection - start ************/
    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    // Rotation will give the same user1 because of singlton
    @Singleton
    @Provides
    @Named("app_user")
    static User someUser(){
        return new User();
    }
}


