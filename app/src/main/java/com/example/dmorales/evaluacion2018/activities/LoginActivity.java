package com.example.dmorales.evaluacion2018.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.FileChooser;
import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.UsuarioLocal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtClave;
    TextView txtAquiMarco;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtClave = (TextInputEditText) findViewById(R.id.login_edtClave);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
        txtAquiMarco = (TextView) findViewById(R.id.login_txtAquiMarco);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar(edtClave.getText().toString());
            }
        });

        txtAquiMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarMarco();
            }
        });
    }

    public void ingresar(String clave){
        try{
            Data data = new Data(LoginActivity.this);
            data.open();
            UsuarioLocal usuarioLocal = data.getUsuarioLocal(clave);
            data.close();
            if (usuarioLocal != null){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("sede", usuarioLocal.getSede());
                startActivity(intent);
            }else{
                Toast.makeText(this, "CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cargarMarco(){
        FileChooser fileChooser = new FileChooser(LoginActivity.this);
        fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                String filename = file.getAbsolutePath();
                Log.d("File", filename);
                Toast.makeText(LoginActivity.this, "Cargando..." + filename, Toast.LENGTH_SHORT).show();
                try {
                    Data data = new Data(LoginActivity.this,filename);
                    data.open();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileChooser.showDialog();
    }
}
