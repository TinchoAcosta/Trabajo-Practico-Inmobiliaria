package com.example.trabajopracticoinmobiliaria.menu.ui.home;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class HomeViewModel extends AndroidViewModel {
    private FusedLocationProviderClient fused;
    private Context context;
    private MutableLiveData<MapaActual> mMapa = new MutableLiveData<>();
    public HomeViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
        fused= LocationServices.getFusedLocationProviderClient(context);
    }
    public LiveData<MapaActual> getMMapa(){
        return mMapa;
    }

    public void cargarMapa(Location ubicacion){
        MapaActual mapaActual = new MapaActual(ubicacion);
        mMapa.setValue(mapaActual);
    }
    public void obtenerUbicacion(){
        Location ubicacion = new Location("ubicacion_manual");
        ubicacion.setLatitude(-33.18442819918452);
        ubicacion.setLongitude(-66.31261489968051);
        cargarMapa(ubicacion);
    }

    public class MapaActual implements OnMapReadyCallback {

        LatLng ubi;

        public MapaActual(Location ubicacion){
            this.ubi = new LatLng(ubicacion.getLatitude(),ubicacion.getLongitude());
        }


        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcador = new MarkerOptions();
            marcador.position(ubi);
            marcador.title("Inmobiliaria");

            googleMap.addMarker(marcador);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            CameraPosition cam = new CameraPosition.Builder()
                    .target(ubi)
                    .zoom(30)
                    .bearing(45)
                    .tilt(15)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cam);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}