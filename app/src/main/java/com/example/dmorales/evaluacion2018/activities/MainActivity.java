package com.example.dmorales.evaluacion2018.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.fragments.ListadoFragment;
import com.example.dmorales.evaluacion2018.fragments.NubeFragment;
import com.example.dmorales.evaluacion2018.fragments.RegistroFragment;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.SQLConstantes;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String sede;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sede = getIntent().getExtras().getString("sede");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegistroFragment registroFragment = new RegistroFragment(sede,MainActivity.this);
        fragmentTransaction.replace(R.id.fragment_layout, registroFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.menu_registro) {
            RegistroFragment registroFragment = new RegistroFragment(sede,MainActivity.this);
            fragmentTransaction.replace(R.id.fragment_layout, registroFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.menu_listado) {
            ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
            fragmentTransaction.replace(R.id.fragment_layout, listadoFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.menu_nube) {
            NubeFragment nubeFragment = new NubeFragment(sede,MainActivity.this);
            fragmentTransaction.replace(R.id.fragment_layout, nubeFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.menu_reset_bd) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Está seguro que desea borrar los datos?")
                    .setTitle("Aviso")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                Data data = new Data(MainActivity.this);
                                data.open();
                                data.deleteAllElementosFromTabla(SQLConstantes.tablafecharegistro);
                                data.close();
                                ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
                                FragmentManager fragmentManage = getSupportFragmentManager();
                                FragmentTransaction fragmentTransact = fragmentManage.beginTransaction();
                                fragmentTransact.replace(R.id.fragment_layout, listadoFragment);
                                fragmentTransact.commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        } else if (id == R.id.menu_cerrar_sesion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Está seguro que desea cerrar sesión en la aplicación?")
                    .setTitle("Aviso")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
