package com.example.trabajopracticoinmobiliaria;

import android.app.Application;
import android.content.Intent;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.menu.MenuActivity;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private String usser,password;
    private MutableLiveData<String> mError = new MutableLiveData<>();
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public void login(Editable Email, Editable Eclave) {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        String mail = Email.toString();
        String clave = Eclave.toString();
        Call<String> llamada = api.login(mail, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token= response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                }
                else
                    mError.postValue("Usuario y/o contraseña Incorrecta; reintente");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mError.postValue("Error de Servidor");

            }
        });
    }
}
