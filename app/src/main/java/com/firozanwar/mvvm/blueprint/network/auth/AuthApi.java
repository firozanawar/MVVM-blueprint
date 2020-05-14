package com.firozanwar.mvvm.blueprint.network.auth;

import com.firozanwar.mvvm.blueprint.model.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {

//    @GET
//    Call<ResponseBody> getUsers();

    // We can use Flowable here from Rx Java by using below depndency becuase retrofit itself doesnt have
    @GET("users/{id}")
    Flowable<User> getUsers(
            @Path("id") int id
    );
}
