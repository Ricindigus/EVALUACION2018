package com.example.dmorales.evaluacion2018.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.NumericKeyBoardTransformationMethod;
import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.DocumentoAsistencia;
import com.example.dmorales.evaluacion2018.modelo.RegistroAsistencia;
import com.example.dmorales.evaluacion2018.modelo.Nacional;
import com.example.dmorales.evaluacion2018.modelo.SQLConstantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntradaFragment extends Fragment {

    ImageView btnBuscar;
    EditText edtDni;

    CardView cvNoregistrado;
    CardView cvYaregistrado;
    CardView cvRegistro;
    CardView cvError;

    TextView txtErrorCargo;
    TextView txtErrorSede;
    TextView txtErrorLocal;

    TextView txtRegistroCargo;
    TextView txtRegistroDni;
    TextView txtRegistroNombres;
    TextView txtRegistroSede;
    TextView txtRegistroLocal;
    TextView txtRegistroAula;

    String sede;
    Context context;
    Data data;



    public EntradaFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public EntradaFragment(String sede, Context context) {
        this.sede = sede;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registro, container, false);

        btnBuscar = (ImageView) rootView.findViewById(R.id.registro_btnBuscar);
        edtDni = (EditText) rootView.findViewById(R.id.registro_edtDni);

        cvError = (CardView) rootView.findViewById(R.id.registro_cvError);
        cvNoregistrado = (CardView) rootView.findViewById(R.id.registro_cvNoRegistrado);
        cvRegistro = (CardView) rootView.findViewById(R.id.registro_cvRegistro);
        cvYaregistrado = (CardView) rootView.findViewById(R.id.registro_cvYaRegistrado);

        txtErrorCargo = (TextView) rootView.findViewById(R.id.registro_error_txtCargo);
        txtErrorLocal = (TextView) rootView.findViewById(R.id.registro_error_txtLocal);
        txtErrorSede = (TextView) rootView.findViewById(R.id.registro_error_txtSede);

        txtRegistroCargo = (TextView) rootView.findViewById(R.id.registro_txtCargo);
        txtRegistroAula = (TextView) rootView.findViewById(R.id.registro_txtAula);
        txtRegistroDni = (TextView) rootView.findViewById(R.id.registro_txtDni);
        txtRegistroLocal = (TextView) rootView.findViewById(R.id.registro_txtLocal);
        txtRegistroNombres = (TextView) rootView.findViewById(R.id.registro_txtNombres);
        txtRegistroSede = (TextView) rootView.findViewById(R.id.registro_txtSede);


        edtDni.setTransformationMethod(new NumericKeyBoardTransformationMethod());
//        edtDni.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    ocultarTeclado(edtDni);
//                    btnBuscar.requestFocus();
//                    return true;
//                }
//                return false;
//            }
//        });
        return rootView;
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 8) clickBoton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBoton();
            }
        });
    }

    public void clickBoton(){
        ocultarTeclado(edtDni);
        btnBuscar.requestFocus();
        String dni = edtDni.getText().toString();
        if(!dni.equals("")){
            if(!buscarDNI(dni)) {
                cvRegistro.setVisibility(View.GONE);
                cvYaregistrado.setVisibility(View.GONE);
                cvError.setVisibility(View.GONE);
                cvNoregistrado.setVisibility(View.VISIBLE);
            }
        }
        edtDni.setText("");
    }

    public boolean buscarDNI(String dni){
        boolean encontrado = false;
        try {
            data = new Data(context);
            data.open();
            Nacional nacional = data.getNacional(dni);
            data.close();
            if(nacional != null){
                encontrado = true;
                if(sede.equals(nacional.getSede())){
                    data = new Data(context);
                    data.open();
                    RegistroAsistencia registroAsistencia = data.getRegistro(nacional.getCodigo());
                    if(registroAsistencia != null){
                        cvError.setVisibility(View.GONE);
                        cvNoregistrado.setVisibility(View.GONE);
                        cvYaregistrado.setVisibility(View.VISIBLE);
                        cvRegistro.setVisibility(View.GONE);
                    }else{
                        cvError.setVisibility(View.GONE);
                        cvNoregistrado.setVisibility(View.GONE);
                        cvYaregistrado.setVisibility(View.GONE);
                        cvRegistro.setVisibility(View.VISIBLE);
                        txtRegistroSede.setText(nacional.getSede());
                        txtRegistroNombres.setText(nacional.getApepat());
                        txtRegistroDni.setText(nacional.getCodigo());
                        txtRegistroLocal.setText(nacional.getLocal_aplicacion());
                        txtRegistroCargo.setText(nacional.getDiscapacidad());
                        txtRegistroAula.setText("Aula " + nacional.getAula());
                        Calendar calendario = Calendar.getInstance();
                        int yy = calendario.get(Calendar.YEAR);
                        int mm = calendario.get(Calendar.MONTH)+1;
                        int dd = calendario.get(Calendar.DAY_OF_MONTH);
                        int hora = calendario.get(Calendar.HOUR_OF_DAY);
                        int minuto = calendario.get(Calendar.MINUTE);
                        final RegistroAsistencia registroAsistencia1 = new RegistroAsistencia(dni,dni,nacional.getApepat(), nacional.getSede(), nacional.getAula(),dd,
                                mm,yy,hora,minuto,0,0,0,-1);
                        data.insertarRegistro(registroAsistencia1);
                        final String fecha = checkDigito(registroAsistencia1.getDia()) + "-" + checkDigito(registroAsistencia1.getMes()) + "-" + registroAsistencia1.getAnio();
                        final String c = registroAsistencia1.getCodigo();
                        registroAsistencia1.setSubidoEntrada(1);
                        //------------------------tipo 1--------------------------------------------------------------
//                        DocumentoAsistencia da = new DocumentoAsistencia(getResources().getString(R.string.id_documento));
//                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        db.collection(getResources().getString(R.string.nombre_coleccion)).document(getResources().getString(R.string.id_documento))
//                                .set(da).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
//                                        FirebaseFirestore.getInstance().collection(getResources().getString(R.string.nombre_coleccion)).document(getResources().getString(R.string.id_documento)).collection(fecha).document(registroAsistencia1.getCodigo()).set(registroAsistencia1)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//                                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
//                                                        try {
//                                                            Data data1 = new Data(context);
//                                                            data1.open();
//                                                            ContentValues contentValues = new ContentValues();
//                                                            contentValues.put(SQLConstantes.registro_subido_entrada,1);
//                                                            data1.actualizarRegistro(c,contentValues);
//                                                            data1.close();
//                                                        } catch (IOException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        Log.w("FIRESTORE", "Error writing document", e);
//                                                    }
//                                                });
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w("FIRESTORE", "Error writing document", e);
//                                    }
//                                });
                        //------------------------tipo 2--------------------------------------------------------------
                        FirebaseFirestore.getInstance().collection(getResources().getString(R.string.nombre_coleccion)).document(getResources().getString(R.string.id_documento))
                                .collection(fecha).document(registroAsistencia1.getCodigo()).set(registroAsistencia1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
                                        try {
                                            Data data1 = new Data(context);
                                            data1.open();
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(SQLConstantes.registro_subido_entrada,1);
                                            data1.actualizarRegistro(c,contentValues);
                                            data1.close();
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
                    data.close();
                }else{
                    cvError.setVisibility(View.VISIBLE);
                    cvNoregistrado.setVisibility(View.GONE);
                    cvRegistro.setVisibility(View.GONE);
                    cvYaregistrado.setVisibility(View.GONE);
                    txtErrorSede.setText(nacional.getSede());
                    txtErrorLocal.setText(nacional.getLocal_aplicacion());
                    txtErrorCargo.setText(nacional.getDiscapacidad());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encontrado;
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
