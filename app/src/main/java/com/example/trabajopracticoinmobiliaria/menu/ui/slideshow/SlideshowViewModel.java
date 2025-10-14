package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mLista = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();

    public SlideshowViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getMLista(){
        return mLista;
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public void cargarLista(){
        String token = ApiClient.leerToken(getApplication());
        Call<List<Inmueble>> llamada = ApiClient.getApiInmobiliaria().obtenerInmuebles("Bearer "+token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
                }else{
                    mError.setValue("Error al obtener inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }
}