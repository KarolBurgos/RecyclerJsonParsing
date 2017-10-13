package com.learn2crack.recyclerjsonparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;
    private Retrofit retrofit;
    public final static String TAG ="DATOS COLOMBIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("----------------","si");
        initViews();

    }
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        Log.e("----------------","si 2");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface service=retrofit.create(RequestInterface.class);
        Call<List<AndroidVersion>>call=service.getJSON();

        call.enqueue(new Callback<List<AndroidVersion>>() {
            @Override
            public void onResponse(Call<List<AndroidVersion>> call, Response<List<AndroidVersion>> response)
            {
               /* JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                Log.e("----------------","si "+data.size());
              */

                if (response.isSuccess()) {
                    List lista=response.body();
                    for(int i=0;i<lista.size();i++){
                        AndroidVersion m=(AndroidVersion) lista.get(i);
                        Log.i(TAG,"Nombre: "+m.getDireccion());
                        adapter = new DataAdapter(data);
                        recyclerView.setAdapter(adapter);

                    }
                } else {
                    Log.e(TAG,"onResponse"+response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<List<AndroidVersion>> call, Throwable t) {

                Log.e(TAG,"onFailure"+t.getMessage());
            }
        });



    /*   Log.e("----------------","si 3");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("----------------","si 4");
        RequestInterface request = retrofit.create(RequestInterface.class);
        Log.e("----------------","si 5");
        Call<JSONResponse> call = request.getJSON();
        Log.e("----------------","si 6");
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.e("----------------","si 7");
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                Log.e("----------------","si "+data.size());
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.e("Error onFailure",t.getMessage());
            }
        });*/
    }
}
