package com.example.trabajopracticoinmobiliaria.menu.ui.gallery;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.models.Propietario;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryViewModel extends AndroidViewModel {

    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();
    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<Integer> mIcono = new MutableLiveData<>();
    private MutableLiveData<String> mNombreBoton = new MutableLiveData<>();
    private String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private String regexTel = "^\\d{6,15}$";
    private String regexDNI = "^\\d{7,8}$";
    private String regexNombre = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$";


    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public LiveData<String> getMExito(){
        return mExito;
    }

    public LiveData<Propietario> getMPropietario(){
        return mPropietario;
    }

    public LiveData<Boolean> getMEstado() {
        return mEstado;
    }

    public LiveData<Integer> getMIcono() {
        return mIcono;
    }

    public LiveData<String> getMNombreBoton() {
        return mNombreBoton;
    }

    public void mostrarPerfil(){
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer "+token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    mPropietario.postValue(response.body());
                }else{
                    mError.setValue("Error al obtener perfil");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mError.setValue("Error de servidor");
            }
        });
    }

    public void editarPerfil(Editable nombre, Editable apellido, Editable dni, Editable tel, Editable email, String tag){
        String nombreS = nombre.toString();
        String apellidoS = apellido.toString();
        String dniS = dni.toString();
        String telS = tel.toString();
        String emailS = email.toString();

        if(tag.equals("editar")){
            mEstado.setValue(true);
            mNombreBoton.setValue("guardar");
            mIcono.setValue(R.drawable.save);
        }else{
            if(nombreS.isEmpty() || apellidoS.isEmpty() || dniS.isEmpty() || telS.isEmpty() || emailS.isEmpty()){
                mError.setValue("Error: No deje campos vacíos");
                return;
            }
            if (!validarCampo(nombreS, regexNombre)) {
                mError.setValue("Error: Nombre inválido");
                return;
            }
            if (!validarCampo(apellidoS, regexNombre)) {
                mError.setValue("Error: Apellido inválido");
                return;
            }
            if (!validarCampo(dniS, regexDNI)) {
                mError.setValue("Error: DNI inválido");
                return;
            }
            if (!validarCampo(telS, regexTel)) {
                mError.setValue("Error: Teléfono inválido");
                return;
            }
            if (!validarCampo(emailS, regexEmail)) {
                mError.setValue("Error: Email inválido");
                return;
            }

            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer "+token,new Propietario(
                    apellidoS,telS,nombreS,mPropietario.getValue().getIdPropietario(),emailS,dniS,null
            ));
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        mExito.setValue("¡Perfil modificado exitosamente!");
                        mEstado.setValue(false);
                        mNombreBoton.setValue("editar");
                        mIcono.setValue(R.drawable.edit);
                    }else{
                        mError.setValue("Error al modificar el perfil");
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    mError.setValue("Error del servidor");
                }
            });

        }

    }

    public boolean validarCampo(String valor, String regex) {
        return valor.matches(regex);
    }
}