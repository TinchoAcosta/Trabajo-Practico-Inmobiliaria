package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentDetalleInquilinoBinding;
import com.example.trabajopracticoinmobiliaria.models.Inquilino;
import com.google.android.material.snackbar.Snackbar;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel mv;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeErrorInquilino.setVisibility(View.VISIBLE);
                binding.tvMensajeErrorInquilino.setText(s);

                binding.tvNombreInquilino.setVisibility(View.GONE);
                binding.tvApellidoInquilino.setVisibility(View.GONE);
                binding.tvDniInquilino.setVisibility(View.GONE);
                binding.tvEmailInquilino.setVisibility(View.GONE);
                binding.tvTelefonoInquilino.setVisibility(View.GONE);

                binding.textView16.setVisibility(View.GONE);
                binding.textView4.setVisibility(View.GONE);
                binding.textView6.setVisibility(View.GONE);
                binding.textView5.setVisibility(View.GONE);
                binding.textView9.setVisibility(View.GONE);
            }
        });

        mv.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvMensajeErrorInquilino.setVisibility(View.GONE);

                binding.tvNombreInquilino.setVisibility(View.VISIBLE);
                binding.tvApellidoInquilino.setVisibility(View.VISIBLE);
                binding.tvDniInquilino.setVisibility(View.VISIBLE);
                binding.tvEmailInquilino.setVisibility(View.VISIBLE);
                binding.tvTelefonoInquilino.setVisibility(View.VISIBLE);

                binding.textView16.setVisibility(View.VISIBLE);
                binding.textView4.setVisibility(View.VISIBLE);
                binding.textView6.setVisibility(View.VISIBLE);
                binding.textView5.setVisibility(View.VISIBLE);
                binding.textView9.setVisibility(View.VISIBLE);

                binding.tvNombreInquilino.setText(inquilino.getNombre());
                binding.tvApellidoInquilino.setText(inquilino.getApellido());
                binding.tvDniInquilino.setText(inquilino.getDni()+"");
                binding.tvEmailInquilino.setText(inquilino.getEmail());
                binding.tvTelefonoInquilino.setText(inquilino.getTelefono()+"");
            }
        });

        mv.cargarInquilino(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}