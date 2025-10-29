package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;


import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.models.Contrato;
import com.example.trabajopracticoinmobiliaria.models.Pago;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagosViewModel extends AndroidViewModel {

    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<List<Pago>> mPagos = new MutableLiveData<>();

    public DetallePagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<List<Pago>> getMPagos(){
        return mPagos;
    }

    public void cargarPagos(Bundle b){
        Contrato contrato = (Contrato) b.getSerializable("contrato");
        if(contrato == null){
            mError.setValue("Error al cargar pagos.");
        }else{
            String token = ApiClient.leerToken(getApplication());
            Call<List<Pago>> llamada = ApiClient.getApiInmobiliaria().cargarPagos("Bearer "+token,contrato.getIdContrato());
            llamada.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    if(response.isSuccessful()){
                        mPagos.postValue(response.body());
                    }else if(response.code() == 404){
                        mError.setValue("Contrato sin pagos.");
                    }else {
                        mError.setValue("Error al cargar los pagos");
                    }
                }

                @Override
                public void onFailure(Call<List<Pago>> call, Throwable t) {
                    mError.setValue("Error del servidor");
                }
            });
        }
    }
}