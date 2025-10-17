package com.example.trabajopracticoinmobiliaria.menu.ui.gallery;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentGalleryBinding;
import com.example.trabajopracticoinmobiliaria.models.Propietario;
import com.google.android.material.snackbar.Snackbar;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleryViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.tvErroresEditarPerfil.setVisibility(View.INVISIBLE);
                binding.etNombreEditar.setText(propietario.getNombre());
                binding.etApellidoEditar.setText(propietario.getApellido());
                binding.etDniEditar.setText(propietario.getDni());
                binding.etEmailEditar.setText(propietario.getEmail());
                binding.etTelEditar.setText(propietario.getTelefono());
            }
        });

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErroresEditarPerfil.setText(s);
                binding.tvErroresEditarPerfil.setVisibility(View.VISIBLE);
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErroresEditarPerfil.setVisibility(View.INVISIBLE);
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombreEditar.setEnabled(aBoolean);
                binding.etApellidoEditar.setEnabled(aBoolean);
                binding.etTelEditar.setEnabled(aBoolean);
                binding.etDniEditar.setEnabled(aBoolean);
                binding.etEmailEditar.setEnabled(aBoolean);
            }
        });

        mv.getMIcono().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.btEditarPerfil.setCompoundDrawablesWithIntrinsicBounds(integer,0,0,0);
            }
        });

        mv.getMNombreBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btEditarPerfil.setText(s.toUpperCase());
                binding.btEditarPerfil.setTag(s);
            }
        });

        binding.tvCambiarContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.cambiarPasswordFragment);
            }
        });

        binding.btEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.btEditarPerfil.getTag().equals("guardar")){
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View view = requireActivity().getCurrentFocus();
                    if (view != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                mv.editarPerfil(
                        binding.etNombreEditar.getText(),
                        binding.etApellidoEditar.getText(),
                        binding.etDniEditar.getText(),
                        binding.etTelEditar.getText(),
                        binding.etEmailEditar.getText(),
                        (String)binding.btEditarPerfil.getTag()
                );
            }
        });

        mv.mostrarPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}