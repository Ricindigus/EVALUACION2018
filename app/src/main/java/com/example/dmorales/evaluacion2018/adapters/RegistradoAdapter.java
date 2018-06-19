package com.example.dmorales.evaluacion2018.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.modelo.Registrado;

import java.util.ArrayList;

public class RegistradoAdapter extends RecyclerView.Adapter<RegistradoAdapter.ViewHolder>{
    ArrayList<Registrado> registrados;
    Context context;

    public RegistradoAdapter(ArrayList<Registrado> registrados, Context context) {
        this.registrados = registrados;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registrado,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Registrado registrado = registrados.get(position);
        holder.txtDni.setText(registrado.getCodigo());
        holder.txtNombres.setText(registrado.getNombres());
        holder.txtSede.setText(registrado.getSede());
        holder.txtAula.setText(registrado.getAula());
        holder.txtFecha.setText(registrado.getDia() + "-" + registrado.getMes() + "-" + registrado.getAnio() + " " +
        registrado.getHora() + ":" + registrado.getMinuto());
//        if(registrado.getSubido() == 1){
//            holder.cv.setCardBackgroundColor(Color.WHITE);
//        }else{
//            holder.cv.setCardBackgroundColor(Color.rgb(227,242,253));
//        }
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public int getItemCount() {
        return registrados.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDni;
        TextView txtNombres;
        TextView txtSede;
        TextView txtAula;
        TextView txtFecha;
        CardView cv;
        public ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.item_registrado_cv);
            txtDni = itemView.findViewById(R.id.item_registrado_txtDni);
            txtNombres = itemView.findViewById(R.id.item_registrado_txtNombres);
            txtSede = itemView.findViewById(R.id.item_registrado_txtSede);
            txtAula = itemView.findViewById(R.id.item_registrado_txtAula);
            txtFecha = itemView.findViewById(R.id.item_registrado_txtFecha);
        }
    }
}
