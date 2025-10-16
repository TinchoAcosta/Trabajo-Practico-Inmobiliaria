package com.example.trabajopracticoinmobiliaria.menu.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.models.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import java.text.DecimalFormat;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolderInmuebles> {
    private List<Inmueble> lista;
    private Context context;
    private LayoutInflater li;
    private OnInmuebleClickListener listener;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");


    public ListAdapter(List<Inmueble> lista, LayoutInflater li, Context context, OnInmuebleClickListener listener) {
        this.lista = lista;
        this.li = li;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderInmuebles onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.lista_adapter_item,parent,false);
        return new ViewHolderInmuebles(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmuebles holder, int position) {
        if (lista.isEmpty()) {
            holder.direccion.setText("No hay inmuebles registrados en el sistema");
            holder.valor.setText("");
        } else {
            Inmueble inmuebleActual = lista.get(position);
            holder.direccion.setText(inmuebleActual.getDireccion());
            String precioFormateado = formatoPrecio.format(inmuebleActual.getValor());
            holder.valor.setText("$ "+precioFormateado);

            String imageUrl = inmuebleActual.getImagen().replace("\\", "/");
            String fullUrl = ApiClient.URLBASE + imageUrl;

            Glide.with(context)
                    .load(fullUrl)
                    .error(R.drawable.inmueble)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.foto);

            holder.ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onVerClick(inmuebleActual);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderInmuebles extends RecyclerView.ViewHolder{
        TextView direccion, valor;
        ImageView foto;
        Button ver;
        public ViewHolderInmuebles(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            valor = itemView.findViewById(R.id.tvPrecio);
            foto = itemView.findViewById(R.id.ivFoto);
            ver = itemView.findViewById(R.id.btVerInmueble);
        }
    }

    public interface OnInmuebleClickListener {
        void onVerClick(Inmueble inmueble);
    }

}
