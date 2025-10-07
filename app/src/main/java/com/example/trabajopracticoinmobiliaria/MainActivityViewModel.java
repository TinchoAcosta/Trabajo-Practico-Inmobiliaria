package com.example.trabajopracticoinmobiliaria;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private String usser,password;
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }

    public void autenticar(Editable usser, Editable password){
        obtenerUsuario();
        if(usser.toString().equals(this.usser) && password.toString().equals(this.password)){
            //todo -> implementar logica de guardar token en preferences
            mExito.setValue("Has ingresado sesión exitosamente.");
        }else{
            mError.setValue("¡No existe el usuario y/o contraseña ingresados!");
        }
    }
    private void obtenerUsuario(){
        usser = "Administrador";
        password = "admin123";
    }
}
