package com.example.dmorales.evaluacion2018.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.adapters.RegistradoAdapter;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.RegistroAsistencia;
import com.example.dmorales.evaluacion2018.modelo.SQLConstantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    ArrayList<RegistroAsistencia> registroAsistencias;
    ArrayList<RegistroAsistencia> agregados;
    String sede;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    boolean b = false;

    public ListadoFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public ListadoFragment(String sede, Context context) {
        this.context = context;
        this.sede = sede;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_listado, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.listado_btnUpload);
        txtNumero = (TextView) rootView.findViewById(R.id.listado_txtNumero);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        final RegistradoAdapter registradoAdapter = new RegistradoAdapter(registroAsistencias,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(registradoAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                agregados = new ArrayList<>();
                try {
                    data = new Data(context);
                    data.open();
                    Calendar calendario = Calendar.getInstance();
                    int yy = calendario.get(Calendar.YEAR);
                    int mm = calendario.get(Calendar.MONTH)+1;
                    int dd = calendario.get(Calendar.DAY_OF_MONTH);
                    agregados = data.getAllSinEnviar(sede,dd,mm,yy);
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(agregados.size() > 0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Toast.makeText(context, "Subiendo...", Toast.LENGTH_SHORT).show();
                    for (final RegistroAsistencia registroAsistencia : agregados){
                        if (registroAsistencia.getSubidoEntrada() == 0)registroAsistencia.setSubidoEntrada(1);
                        if (registroAsistencia.getSubidoSalida() == 0)registroAsistencia.setSubidoSalida(1);
                        String fecha = checkDigito(registroAsistencia.getDia()) + "-" + checkDigito(registroAsistencia.getMes()) + "-" + registroAsistencia.getAnio();
                        final String c = registroAsistencia.getCodigo();
                        db.collection(getResources().getString(R.string.nombre_coleccion)).document(getResources().getString(R.string.id_documento)).collection(fecha).document(registroAsistencia.getCodigo()).set(registroAsistencia)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
                                        if(!b){
                                            Toast.makeText(context, agregados.size() +" registros subidos", Toast.LENGTH_SHORT).show();
                                            b =true;
                                        }
                                        try {
                                            data = new Data(context);
                                            data.open();
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(SQLConstantes.registro_subido_entrada,1);
                                            contentValues.put(SQLConstantes.registro_subido_salida,1);
                                            data.actualizarRegistro(c,contentValues);
                                            cargaData();
                                            registradoAdapter.notifyDataSetChanged();
                                            data.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("FIRESTORE", "Error writing document", e);
                                    }
                                });
                    }
                }else{
                    Toast.makeText(context, "No hay registros nuevos para subir", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cargaData(){
        registroAsistencias = new ArrayList<>();
        try {
            Data data = new Data(context);
            data.open();
            Calendar calendario = Calendar.getInstance();
            int yy = calendario.get(Calendar.YEAR);
            int mm = calendario.get(Calendar.MONTH)+1;
            int dd = calendario.get(Calendar.DAY_OF_MONTH);
            registroAsistencias = data.getAllRegistrados(sede,dd,mm,yy);
            txtNumero.setText("Total registros: " + registroAsistencias.size());
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
