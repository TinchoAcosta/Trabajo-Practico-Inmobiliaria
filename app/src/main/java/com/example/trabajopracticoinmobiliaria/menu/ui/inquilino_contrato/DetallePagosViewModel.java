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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                        List<Pago> pagos = response.body();
                        if (pagos != null || !pagos.isEmpty()){
                            for (Pago p:pagos) {
                                String fechaNueva = formatearFechas(p.getFechaPago());
                                p.setFechaPago(fechaNueva);
                            }
                            mPagos.setValue(pagos);
                        }
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