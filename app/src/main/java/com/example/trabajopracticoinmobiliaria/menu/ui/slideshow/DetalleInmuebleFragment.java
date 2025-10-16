package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;
import com.google.android.material.snackbar.Snackbar;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.cbDisponibleDetalleInmueble.setChecked(inmueble.isDisponible());
                binding.tvDireccionDetalleInmueble.setText(inmueble.getDireccion());
                binding.tvAmbientesDetalleInmueble.setText(""+inmueble.getAmbientes());
                binding.tvTipoDetalleInmueble.setText(inmueble.getTipo());
                binding.tvUsoDetalleInmueble.setText(inmueble.getUso());
                binding.tvLatDetalleInmueble.setText(String.format("%.2f",inmueble.getLatitud()));
                binding.tvLongDetalleInmueble.setText(String.format("%.2f",inmueble.getLongitud()));
                binding.tvValorDetalleInmueble.setText(String.format("$ %.2f",inmueble.getValor()));
                binding.tvSuperficieDetalleInmueble.setText(String.format("%.2f",inmueble.getSuperficie()));

                String imageUrl = inmueble.getImagen().replace("\\", "/");
                String fullUrl = ApiClient.URLBASE + imageUrl;

                Glide.with(getContext())
                        .load(fullUrl)
                        .error(R.drawable.inmueble)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivFotoDetalleInmueble);
            }
        });

        mv.cargarInmueble(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}