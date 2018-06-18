package com.example.dmorales.evaluacion2018.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.UsuarioLocal;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtClave;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtClave = (TextInputEditText) findViewById(R.id.login_edtClave);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar(edtClave.getText().toString());
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
}
