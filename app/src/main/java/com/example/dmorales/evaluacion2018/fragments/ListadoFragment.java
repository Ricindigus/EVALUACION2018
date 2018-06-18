package com.example.dmorales.evaluacion2018.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.adapters.RegistradoAdapter;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.Registrado;
import com.example.dmorales.evaluacion2018.modelo.SQLConstantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    ArrayList<Registrado> registrados;
    ArrayList<Registrado> agregados;
    String sede;
    Data data;
    FloatingActionButton fabUpLoad;

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
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        RegistradoAdapter registradoAdapter = new RegistradoAdapter(registrados,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(registradoAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregados = new ArrayList<>();
                try {
                    data = new Data(context);
                    data.open();
                    agregados = data.getAllRegistradosTemporal();
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(agregados.size() > 0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    for (final Registrado registrado : agregados){
                        registrado.setSubido(1);
                        final String c = registrado.getCodigo();
                        db.collection("asistencias").document(registrado.getCodigo()).set(registrado)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
                                        try {
                                            data = new Data(context);
                                            data.open();
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(SQLConstantes.fecha_de_registro_subido,1);
                                            data.actualizarFechaRegistro(c,contentValues);
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
        registrados = new ArrayList<>();
        try {
            Data data = new Data(context);
            data.open();
            registrados = data.getAllRegistrados(sede);
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
