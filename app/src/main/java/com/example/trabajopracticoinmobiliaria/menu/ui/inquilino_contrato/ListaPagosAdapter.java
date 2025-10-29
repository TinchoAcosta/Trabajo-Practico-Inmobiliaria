package com.example.trabajopracticoinmobiliaria.menu.ui.inquilino_contrato;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.models.Pago;

import java.text.DecimalFormat;
import java.util.List;

public class ListaPagosAdapter extends RecyclerView.Adapter<ListaPagosAdapter.ViewHolderPagos>{

    private List<Pago> lista;
    private Context context;
    private LayoutInflater li;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");

    public ListaPagosAdapter(List<Pago> lista, LayoutInflater li, Context context) {
        this.lista = lista;
        this.li = li;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPagos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.pagos_item,parent,false);
        return new ListaPagosAdapter.ViewHolderPagos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPagos holder, int position) {
        if(lista.isEmpty()){
            holder.fecha.setVisibility(View.GONE);
            holder.estado.setVisibility(View.GONE);
            holder.monto.setVisibility(View.GONE);
            holder.detalle.setText("¡No existen pagos!");
        }else {
            Pago pagoActual = lista.get(position);

            holder.fecha.setVisibility(View.VISIBLE);
            holder.estado.setVisibility(View.VISIBLE);
            holder.monto.setVisibility(View.VISIBLE);

            holder.fecha.setText(pagoActual.getFechaPago());
            String precioFormateado = formatoPrecio.format(pagoActual.getMonto());
            holder.monto.setText("$ "+precioFormateado);
            if(pagoActual.isEstado()){
                holder.estado.setText("VALIDADO");
                holder.estado.setTextColor(Color.GREEN);
            }else {
                holder.estado.setText("EN REVISION");
                holder.estado.setTextColor(Color.YELLOW);
            }
            holder.detalle.setText(pagoActual.getDetalle());
        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderPagos extends RecyclerView.ViewHolder{
        TextView fecha, monto, detalle, estado;
        public ViewHolderPagos(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFechaPagoD);
            monto = itemView.findViewById(R.id.tvMontoPagoD);
            detalle = itemView.findViewById(R.id.tvDetallePagoD);
            estado = itemView.findViewById(R.id.tvEstadoPagoD);
        }
    }
}
