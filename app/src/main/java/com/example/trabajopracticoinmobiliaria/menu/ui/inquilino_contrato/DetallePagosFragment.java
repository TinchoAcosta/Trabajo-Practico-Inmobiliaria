package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentDetallePagosBinding;
import com.example.trabajopracticoinmobiliaria.models.Pago;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DetallePagosFragment extends Fragment {

    private DetallePagosViewModel mv;
    private FragmentDetallePagosBinding binding;

    public static DetallePagosFragment newInstance() {
        return new DetallePagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetallePagosViewModel.class);
        binding = FragmentDetallePagosBinding.inflate(inflater, container, false);
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

        mv.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                ListaPagosAdapter lpa = new ListaPagosAdapter(pagos,getLayoutInflater(),getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvListaPagos.setLayoutManager(glm);
                binding.rvListaPagos.setAdapter(lpa);
            }
        });

        mv.cargarPagos(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}