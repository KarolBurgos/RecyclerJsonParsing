package com.learn2crack.recyclerjsonparsing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("pfet-63uw.json")
    Call<List<AndroidVersion>> getJSON();
}
