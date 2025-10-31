package com.example.trabajopracticoinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.trabajopracticoinmobiliaria.models.Contrato;
import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.models.Pago;
import com.example.trabajopracticoinmobiliaria.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {

    public static final String URLBASE= "http://{ip}:5090/";

    public static InmobiliariaService getApiInmobiliaria(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);

    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public interface InmobiliariaService{

        @FormUrlEncoded
        @POST("api/Propietario/login")
        Call<String> login(@Field("Usuario") String u, @Field("Clave") String c);

        @GET("api/Propietario")
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);

        @GET("api/Inmueble")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        @PUT("api/Propietario/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario p);

        @FormUrlEncoded
        @PUT("api/Propietario/changePassword")
        Call<Void> cambiarPassword(@Header("Authorization") String token, @Field("currentPassword") String cActual, @Field("newPassword") String cNueva);

        @PUT("api/Inmueble/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble i);

        @Multipart
        @POST("api/Inmueble/cargar")
        Call<Inmueble> cargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble")RequestBody inmueble);

        @GET("api/contrato/inmueble/{id}")
        Call<Contrato> cargarContrato(@Header("Authorization") String token, @Path("id") int idInmueble);

        @GET("api/pago/contrato/{id}")
        Call<List<Pago>> cargarPagos(@Header("Authorization") String token, @Path("id") int idContrato);
    }
}
