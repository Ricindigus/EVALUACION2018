package com.example.dmorales.evaluacion2018.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.dmorales.evaluacion2018.R;
import com.example.dmorales.evaluacion2018.adapters.ExpandListAdapter;
import com.example.dmorales.evaluacion2018.fragments.ListadoFragment;
import com.example.dmorales.evaluacion2018.fragments.NubeFragment;
import com.example.dmorales.evaluacion2018.fragments.EntradaFragment;
import com.example.dmorales.evaluacion2018.fragments.SalidaFragment;
import com.example.dmorales.evaluacion2018.modelo.Data;
import com.example.dmorales.evaluacion2018.modelo.SQLConstantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String sede;
    private ArrayList<String> listDataHeader;
    private ExpandableListView expListView;
    private HashMap<String, List<String>> listDataChild;
    private ExpandListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sede = getIntent().getExtras().getString("sede");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                ocultarTeclado(drawerView);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        enableExpandableList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EntradaFragment entradaFragment = new EntradaFragment(sede,MainActivity.this);
        fragmentTransaction.replace(R.id.fragment_layout, entradaFragment);
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

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void enableExpandableList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (groupPosition){
                    case 0:
                        switch (childPosition){
                            case 0:
                                Toast.makeText(MainActivity.this, "Ingreso de cajas al local: Cajas", Toast.LENGTH_SHORT).show();break;
                        }
                        break;
                    case 1:
                        switch (childPosition){
                            case 0:
                                Toast.makeText(MainActivity.this, "Registro de control de Asistencia: Local", Toast.LENGTH_SHORT).show();break;
                            case 1:
                                Toast.makeText(MainActivity.this, "Registro de control de Asistencia: Aula", Toast.LENGTH_SHORT).show();break;
                        }
                        break;
                    case 2:
                        switch (childPosition){
                            case 0:
                                Toast.makeText(MainActivity.this, "Registro de Control de Inventario: Ficha", Toast.LENGTH_SHORT).show();break;
                            case 1:
                                Toast.makeText(MainActivity.this, "Registro de Control de Inventario: Cuadernillo", Toast.LENGTH_SHORT).show();break;
                            case 2:
                                Toast.makeText(MainActivity.this, "Registro de Control de Inventario: Lista de Asistencia", Toast.LENGTH_SHORT).show();break;
                        }
                        break;
                    case 3:
                        switch (childPosition){
                            case 0:
                                Toast.makeText(MainActivity.this, "Salida de Cajas del Local: Cajas", Toast.LENGTH_SHORT).show();break;
                        }
                        break;
                    case 4:
                        switch (childPosition){
                            case 0:
                                Toast.makeText(MainActivity.this, "reportes: Listado ingreso cajas al local", Toast.LENGTH_SHORT).show();break;
                            case 1:
                                Toast.makeText(MainActivity.this, "reportes: Listado de asistencia local", Toast.LENGTH_SHORT).show();break;
                            case 3:
                                Toast.makeText(MainActivity.this, "reportes: Listado de asistencia por aula", Toast.LENGTH_SHORT).show();break;
                            case 4:
                                Toast.makeText(MainActivity.this, "reportes: Listado de inventario - ficha", Toast.LENGTH_SHORT).show();break;
                            case 5:
                                Toast.makeText(MainActivity.this, "reportes: Listado de inventario - cuadernillo", Toast.LENGTH_SHORT).show();break;
                            case 6:
                                Toast.makeText(MainActivity.this, "reportes: Listado inventario - listado de asistencia", Toast.LENGTH_SHORT).show();break;
                            case 7:
                                Toast.makeText(MainActivity.this, "reportes: Listado salida cajas del local", Toast.LENGTH_SHORT).show();break;
                            case 8:
                                Toast.makeText(MainActivity.this, "reportes: cuadro resumen ingreso de cajas", Toast.LENGTH_SHORT).show();break;
                            case 9:
                                Toast.makeText(MainActivity.this, "reportes: cuadro resumen asistencia", Toast.LENGTH_SHORT).show();break;
                            case 10:
                                Toast.makeText(MainActivity.this, "reportes: cuadro resumen inventario", Toast.LENGTH_SHORT).show();break;
                            case 11:
                                Toast.makeText(MainActivity.this, "reportes: cuadro resumen salida de cajas", Toast.LENGTH_SHORT).show();break;
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void prepareListData(List<String> listDataHeader, Map<String, List<String>> listDataChild) {

        // Adding child data
        listDataHeader.add("Ingreso de Cajas al Local");
        listDataHeader.add("Registro de Control de Asistencia");
        listDataHeader.add("Registro de control de Inventario");
        listDataHeader.add("Salida de Cajas del Local");
        listDataHeader.add("Reportes");
        listDataHeader.add("Consulta Padron Nacional");
        listDataHeader.add("Mas Opciones");


        // Adding child data
        List<String> ingresoCajas = new ArrayList<String>();
        ingresoCajas.add("Cajas");

        List<String> registroAsistencia = new ArrayList<String>();
        registroAsistencia.add("Local");
        registroAsistencia.add("Aula");

        List<String> registroInventario = new ArrayList<String>();
        registroInventario.add("Ficha");
        registroInventario.add("Cuadernillo");
        registroInventario.add("Lista de Asistencia");

        List<String> salidaCajas = new ArrayList<String>();
        salidaCajas.add("Cajas");

        List<String> reportes = new ArrayList<String>();
        reportes.add("Listado Ingreso Cajas a Local");
        reportes.add("Listado de Asistencia Local");
        reportes.add("Listado de asistencia por Aula");
        reportes.add("Listado de Inventario - Ficha");
        reportes.add("Listado de Inventario - Cuadernillo");
        reportes.add("Listado de Inventario - Listado de Asistencia");
        reportes.add("Listado Salida de cajas del local");
        reportes.add("Cuadro Resumen Ingreso Cajas");
        reportes.add("Cuadro Resumen Asistencia");
        reportes.add("Cuadro Resumen Inventario");
        reportes.add("Cuadro Resumen Salida Cajas");

        List<String> consultaPadron = new ArrayList<String>();
        consultaPadron.add("Consulta Padrón Nacional");

        List<String> masOpciones = new ArrayList<String>();
        masOpciones.add("Reset BD");


        listDataChild.put(listDataHeader.get(0), ingresoCajas);// Header, Child data
        listDataChild.put(listDataHeader.get(1), registroAsistencia);
        listDataChild.put(listDataHeader.get(2), registroInventario);
        listDataChild.put(listDataHeader.get(3), salidaCajas);
        listDataChild.put(listDataHeader.get(4), reportes);
        listDataChild.put(listDataHeader.get(5), consultaPadron);
        listDataChild.put(listDataHeader.get(6), masOpciones);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.showHome) {
//            Toast.makeText(this, "showHome", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        int id = item.getItemId();
//
//        if (id == R.id.menu_registro_entrada) {
//            EntradaFragment entradaFragment = new EntradaFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, entradaFragment);
//            fragmentTransaction.commit();
//        } else if (id == R.id.menu_registro_salida) {
//            SalidaFragment salidaFragment = new SalidaFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, salidaFragment);
//            fragmentTransaction.commit();
//        } else if (id == R.id.menu_listado) {
//            ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, listadoFragment);
//            fragmentTransaction.commit();
//        } else if (id == R.id.menu_nube) {
//            NubeFragment nubeFragment = new NubeFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, nubeFragment);
//            fragmentTransaction.commit();
//
//        } else if (id == R.id.menu_reset_bd) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("¿Está seguro que desea borrar los datos?")
//                    .setTitle("Aviso")
//                    .setCancelable(false)
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    })
//                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            try {
//                                Data data = new Data(MainActivity.this);
//                                data.open();
//                                data.deleteAllElementosFromTabla(SQLConstantes.tablaregistro);
//                                data.close();
//                                ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
//                                FragmentManager fragmentManage = getSupportFragmentManager();
//                                FragmentTransaction fragmentTransact = fragmentManage.beginTransaction();
//                                fragmentTransact.replace(R.id.fragment_layout, listadoFragment);
//                                fragmentTransact.commit();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//
//        } else if (id == R.id.menu_cerrar_sesion) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("¿Está seguro que desea cerrar sesión en la aplicación?")
//                    .setTitle("Aviso")
//                    .setCancelable(false)
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            })
//                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}
