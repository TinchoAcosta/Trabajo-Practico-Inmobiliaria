package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.models.Inmueble;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public void cargarInmueble(Bundle b){
        Inmueble inm = (Inmueble) b.getSerializable("inmueble");
        if(inm == null){
            mError.setValue("Error al cargar el inmueble");
        }else{
            mInmueble.setValue(inm);
        }
    }
}