package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.google.android.material.snackbar.Snackbar;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel mv;
    private FragmentAgregarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arlG;
    private ActivityResultLauncher<Intent> arlC;
    private Intent intentG;
    private Intent intentC;

    public static AgregarInmuebleFragment newInstance() {
        return new AgregarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(AgregarInmuebleViewModel.class);
        binding = FragmentAgregarInmuebleBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        abrirGaleria();

        binding.btAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlG.launch(intentG);
            }
        });

        binding.btTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //arlC.launch(intentC);
            }
        });

        mv.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFotoElegida.setImageURI(uri);
            }
        });

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.pbCargando.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.pbCargando.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        binding.btAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = requireActivity().getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                binding.pbCargando.setVisibility(View.VISIBLE);

                mv.cargarInmueble(binding.etDireccionAI.getText().toString(),
                        binding.etValorAI.getText().toString(),
                        binding.spTipoAI.getSelectedItem().toString(),
                        binding.spUsoAI.getSelectedItem().toString(),
                        binding.etAmbientesAI.getText().toString(),
                        binding.etSuperficieAI.getText().toString(),
                        binding.etLatAI.getText().toString(),
                        binding.etLongAI.getText().toString(),
                        binding.cbDisponibleAI.isChecked());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void abrirGaleria() {
        intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //abre la galeria
        arlG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mv.recibirFoto(result);
            }
        });
    }



}