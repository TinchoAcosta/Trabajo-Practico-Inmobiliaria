package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();
    private static final String LATITUD_REGEX = "^-?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$";
    private static final String LONGITUD_REGEX = "^-?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";
    private static final String ENTERO_REGEX = "^\\d+$";
    private static final String PRECIO_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMUri(){
        return mUri;
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public LiveData<String> getMExito(){
        return mExito;
    }

    public void recibirFoto(ActivityResult result){
            if (result.getResultCode() == RESULT_OK) { //if controla que se cerro la galeria eligiendo y no cancelando
                Intent data = result.getData();
                Uri uri = data.getData();
                mUri.setValue(uri);
            }
    }

    public void recibirFotoDeCamara(ActivityResult result, Uri uri) {
        if (result.getResultCode() == RESULT_OK) {
            // La URI ya la tenemos, solo necesitamos confirmar que la operación fue exitosa
            mUri.setValue(uri);
        }
    }

    public void cargarInmueble(String direccion,
                               String valor,
                               String tipo,
                               String uso,
                               String ambientes,
                               String superficie,
                               String latitud,
                               String longitud,
                               boolean disponible){

        if(direccion.isBlank() ||
                valor.isBlank() ||
                ambientes.isBlank() ||
                superficie.isBlank() ||
                latitud.isBlank() ||
                longitud.isBlank()){
            mError.setValue("Error: No deje campos vacíos");
            return;
        }

        if(tipo.equalsIgnoreCase("Seleccione un tipo")){
            mError.setValue("Error: Seleccione un tipo para el inmueble");
            return;
        }
        if(uso.equalsIgnoreCase("Seleccione un uso")){
            mError.setValue("Error: Seleccione un uso para el inmueble");
            return;
        }
        if(!valor.matches(PRECIO_REGEX)){
            mError.setValue("Error: Ingrese un valor válido");
            return;
        }
        if(!ambientes.matches(ENTERO_REGEX) || !superficie.matches(ENTERO_REGEX)){
            mError.setValue("Error: Ingrese un numero valido para ambientes y/o superficie");
            return;
        }
        if(!latitud.matches(LATITUD_REGEX)){
            mError.setValue("Error: Ingrese una latitud válida (-90 - 90)");
            return;
        }
        if(!longitud.matches(LONGITUD_REGEX)){
            mError.setValue("Error: Ingrese una longitud válida (-180 - 180)");
            return;
        }
        if(mUri.getValue() == null){
            mError.setValue("Error: Porfavor seleccione una imagen para la portada del inmueble");
            return;
        }

        double precioP = Double.parseDouble(valor);
        int superficieP = Integer.parseInt(superficie);
        int ambientesP = Integer.parseInt(ambientes);
        double latP = Double.parseDouble(latitud);
        double longP = Double.parseDouble(longitud);

        Inmueble inm = new Inmueble();
        inm.setDireccion(direccion);
        inm.setTipo(tipo);
        inm.setUso(uso);
        inm.setValor(precioP);
        inm.setSuperficie(superficieP);
        inm.setAmbientes(ambientesP);
        inm.setLatitud(latP);
        inm.setLongitud(longP);
        inm.setDisponible(disponible);

        byte[] imagen = transformarImagen();
        String inmJson = new Gson().toJson(inm);

        RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmJson);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);

        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(getApplication());

        Call<Inmueble> llamada = api.cargarInmueble("Bearer "+token,imagenPart,inmuebleBody);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    mExito.setValue("¡Inmueble añadido exitósamente!");
                }else{
                    mError.setValue("Error al cargar el inmueble");
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });

    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();  //lo puedo usar porque estoy en viewmodel
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {
            Toast.makeText(getApplication(), "No ha seleccinado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }
}