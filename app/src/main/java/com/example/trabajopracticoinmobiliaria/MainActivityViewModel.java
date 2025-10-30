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
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<Boolean> mAgitar = new MutableLiveData<>();
    private String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<Boolean> getMAgitar(){
        return mAgitar;
    }

    public void login(Editable Email, Editable Eclave) {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        String mail = Email.toString();
        String clave = Eclave.toString();
        if(mail.isEmpty()){
            mError.setValue("Ingrese un mail");
            return;
        }
        if(clave.isEmpty()){
            mError.setValue("Ingrese una clave");
            return;
        }
        if(!mail.matches(regexEmail)){
            mError.setValue("Ingrese un mail válido");
            return;
        }
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

    public void controlAcelerometro(float x, float y, float z){
        float acceleration = (float) Math.sqrt(x * x + y * y + z * z);
        if (acceleration > 12) {
            mAgitar.setValue(true);
        }
    }

    public void resetearAcelerometro(){
        mAgitar.setValue(false);
    }
}
