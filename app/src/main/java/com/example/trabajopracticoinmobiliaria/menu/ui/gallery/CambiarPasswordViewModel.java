package com.example.trabajopracticoinmobiliaria.menu.ui.gallery;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();

    public CambiarPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }

    public void cambiarContra(Editable actual, Editable nueva, Editable nueva1){
        String contraActual = actual.toString();
        String contraNueva = nueva.toString();
        String confirmacion = nueva1.toString();
        if(contraNueva.isEmpty() || contraActual.isEmpty() || confirmacion.isEmpty()){
            mError.setValue("Error: No deje campos vacíos");
            return;
        }
        if(!contraNueva.equals(confirmacion)){
            mError.setValue("Error: La nueva contraseña y su confirmación no coinciden");
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(getApplication());
        Call<Void> llamada = api.cambiarPassword("Bearer "+token,contraActual,contraNueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mExito.setValue("¡Contraseña actualizada!");
                }else{
                    mError.setValue("Error al cambiar la contraseña");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });

    }
}