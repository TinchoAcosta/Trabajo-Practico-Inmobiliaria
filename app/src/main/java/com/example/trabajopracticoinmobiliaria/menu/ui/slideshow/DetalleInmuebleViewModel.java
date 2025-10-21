package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public LiveData<String> getMExito(){
        return mExito;
    }

    public void cargarInmueble(Bundle b){
        Inmueble inm = (Inmueble) b.getSerializable("inmueble");
        if(inm == null){
            mError.setValue("Error al cargar el inmueble");
        }else{
            mInmueble.setValue(inm);
        }
    }

    public void actualizar(boolean estado){
        Inmueble i = new Inmueble();
        i.setDisponible(estado);
        i.setIdInmueble(mInmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = ApiClient.getApiInmobiliaria().actualizarInmueble("Bearer "+token,i);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    if(estado){
                        mExito.setValue("El inmueble indicado ahora se encuentra disponible.");
                    }else{
                        mExito.setValue("El inmueble indicado ahora NO se encuentra disponible.");
                    }
                }else {
                    mError.setValue("Error al actualizar el inmueble.");
                    Log.d("API_ERROR", response.message()+" ------- "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }
}