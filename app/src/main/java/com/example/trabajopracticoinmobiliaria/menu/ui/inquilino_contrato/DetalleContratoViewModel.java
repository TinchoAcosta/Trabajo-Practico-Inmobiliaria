package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.models.Contrato;
import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<Boolean> getMEstado(){
        return mEstado;
    }
    public LiveData<Contrato> getMContrato(){
        return mContrato;
    }

    public void cargarContrato(Bundle b){
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
                        if (contrato != null) {
                            String fechaI = formatearFechas(contrato.getFechaInicio());
                            String fechaF = formatearFechas(contrato.getFechaFinalizacion());
                            contrato.setFechaInicio(fechaI);
                            contrato.setFechaFinalizacion(fechaF);
                            mContrato.setValue(contrato);
                            mEstado.setValue(contrato.isEstado());
                        }
                    }else if(response.code() == 404){
                        mError.setValue("Inmueble sin contrato.");
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

    public String formatearFechas(String fechaOrigen){
        try {
            // Formato original: "yyyy-MM-dd"
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // Formato deseado: "dd/MM/yyyy"
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            // Parsear la fecha original
            Date fecha = formatoEntrada.parse(fechaOrigen);

            // Formatear a nuevo formato
            return formatoSalida.format(fecha);
        } catch (Exception e) {
            return fechaOrigen;
        }
    }
}