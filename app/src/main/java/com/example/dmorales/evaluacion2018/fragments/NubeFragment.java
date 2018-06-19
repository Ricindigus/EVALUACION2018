package com.example.dmorales.evaluacion2018.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.adapters.RegistradoAdapter;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.Registrado;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NubeFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    ArrayList<Registrado> registrados;
    String sede;
    TextView txtNumero;

    public NubeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public NubeFragment(String sede, Context context) {
        this.context = context;
        this.sede = sede;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nube, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.nube_recycler);
        txtNumero = (TextView) rootView.findViewById(R.id.nube_txtNumero);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        final RegistradoAdapter registradoAdapter = new RegistradoAdapter(registrados,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(registradoAdapter);
    }

    public void cargaData(){
        registrados = new ArrayList<>();
        try {
            Data data = new Data(context);
            data.open();
            registrados = data.getAllRegistradosNube();
            txtNumero.setText("Total enviados: " + registrados.size());
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
