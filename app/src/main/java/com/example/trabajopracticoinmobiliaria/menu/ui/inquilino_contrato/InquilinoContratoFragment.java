package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentInquilinoContratoBinding;
import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class InquilinoContratoFragment extends Fragment {

    private InquilinoContratoViewModel mv;
    private FragmentInquilinoContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InquilinoContratoViewModel.class);
        binding = FragmentInquilinoContratoBinding.inflate(inflater,container,false);
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

        mv.getMLista().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                ListaAdapter la = new ListaAdapter(inmuebles, getLayoutInflater(), getContext(), new ListaAdapter.OnInmuebleIClickListener() {
                    @Override
                    public void onVerClick(Inmueble inmueble) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("inmueble",inmueble);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.detalleInquilinoFragment,bundle);
                    }
                }, new ListaAdapter.OnInmuebleCClickListener() {
                    @Override
                    public void onVerClick(Inmueble inmueble) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("inmueble",inmueble);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.detalleContratoFragment,bundle);
                    }
                });
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvListaIyC.setLayoutManager(glm);
                binding.rvListaIyC.setAdapter(la);
            }
        });

        mv.cargarLista();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}