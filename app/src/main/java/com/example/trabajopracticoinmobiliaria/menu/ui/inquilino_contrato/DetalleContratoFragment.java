package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.example.trabajopracticoinmobiliaria.models.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mv;
    public FragmentDetalleContratoBinding binding;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textViewFechaInicioDC.setVisibility(View.GONE);
                binding.tvFechaInicioDC.setVisibility(View.GONE);
                binding.textViewFechaFinDC.setVisibility(View.GONE);
                binding.tvFechaFinDC.setVisibility(View.GONE);
                binding.textViewMontoDC.setVisibility(View.GONE);
                binding.tvMontoDC.setVisibility(View.GONE);
                binding.textViewEstadoDC.setVisibility(View.GONE);
                binding.tvEstadoDC.setVisibility(View.GONE);
                binding.textViewDireccionInmuebleDC.setVisibility(View.GONE);
                binding.tvDireccionInmuebleDC.setVisibility(View.GONE);
                binding.textViewNombreContratoDC.setVisibility(View.GONE);
                binding.tvNombreInquilinoDC.setVisibility(View.GONE);
                binding.btVerPagosDC.setVisibility(View.GONE);

                binding.tvMensajeErrorContratoDC.setVisibility(View.VISIBLE);
                binding.tvMensajeErrorContratoDC.setText(s);
            }
        });

        mv.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.textViewFechaInicioDC.setVisibility(View.VISIBLE);
                binding.tvFechaInicioDC.setVisibility(View.VISIBLE);
                binding.textViewFechaFinDC.setVisibility(View.VISIBLE);
                binding.tvFechaFinDC.setVisibility(View.VISIBLE);
                binding.textViewMontoDC.setVisibility(View.VISIBLE);
                binding.tvMontoDC.setVisibility(View.VISIBLE);
                binding.textViewEstadoDC.setVisibility(View.VISIBLE);
                binding.tvEstadoDC.setVisibility(View.VISIBLE);
                binding.textViewDireccionInmuebleDC.setVisibility(View.VISIBLE);
                binding.tvDireccionInmuebleDC.setVisibility(View.VISIBLE);
                binding.textViewNombreContratoDC.setVisibility(View.VISIBLE);
                binding.tvNombreInquilinoDC.setVisibility(View.VISIBLE);
                binding.btVerPagosDC.setVisibility(View.VISIBLE);

                binding.tvMensajeErrorContratoDC.setVisibility(View.GONE);

                binding.tvFechaInicioDC.setText(contrato.getFechaInicio());
                binding.tvFechaFinDC.setText(contrato.getFechaFinalizacion());
                binding.tvMontoDC.setText(String.format("$ %.2f",contrato.getMontoAlquiler()));
                binding.tvDireccionInmuebleDC.setText(contrato.getInmueble().getDireccion());
                binding.tvNombreInquilinoDC.setText(contrato.getInquilino().getNombre()+" "+contrato.getInquilino().getApellido());

                binding.btVerPagosDC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato",contrato);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.detallePagosFragment,bundle);
                    }
                });
            }
        });

        mv.getMEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.tvEstadoDC.setText("ACTIVO");
                    binding.tvEstadoDC.setTextColor(Color.GREEN);
                }else {
                    binding.tvEstadoDC.setText("INACTIVO");
                    binding.tvEstadoDC.setTextColor(Color.RED);
                }
            }
        });

        mv.cargarContrato(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}