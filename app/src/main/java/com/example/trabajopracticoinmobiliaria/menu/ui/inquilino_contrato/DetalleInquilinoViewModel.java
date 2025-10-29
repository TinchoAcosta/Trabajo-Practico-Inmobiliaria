package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.models.Contrato;
import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.models.Inquilino;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<String> mError= new MutableLiveData<>();
    private MutableLiveData<Inquilino> mInquilino= new MutableLiveData<>();

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<Inquilino> getMInquilino(){
        return mInquilino;
    }

    public void cargarInquilino(Bundle b){
        Inmueble inm = (Inmueble) b.getSerializable("inmueble");
        if(inm == null){
            mError.setValue("Error al cargar el inmueble");
        }else{
            String token = ApiClient.leerToken(getApplication());
            Call<Contrato> llamada = ApiClient.getApiInmobiliaria().cargarContrato("Bearer "+token,inm.getIdInmueble());
            llamada.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if(response.isSuccessful()){
                        Contrato contrato = response.body();
                        Inquilino inquilino = contrato.getInquilino();
                        mInquilino.setValue(inquilino);
                    }else if(response.code() == 404){
                        mError.setValue("Inmueble sin inquilino.");
                    }else {
                        mError.setValue("Error al cargar contrato");
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable t) {
                    mError.setValue("Error del servidor");
                }
            });
        }
    }
}