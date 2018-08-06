package com.example.dmorales.evaluacion2018.modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Data {
    Context contexto;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public Data(Context contexto) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
        createDataBase();
    }

    public Data(Context contexto, String inputPath) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
        createDataBase(inputPath);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.close();
            try{
                copyDataBase();
                sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_REGISTRO);
//                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_FECHA_REGISTRO_TEMPORAL);
                sqLiteDatabase.close();
            }catch (IOException e){
                throw new Error("Error: copiando base de datos");
            }
        }

    }

    @SuppressLint("NewApi")
    public void createDataBase(String inputPath) throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            SQLiteDatabase.deleteDatabase(dbFile);
        }
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.close();
        try{
            copyDataBase(inputPath);
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_REGISTRO);
//            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_FECHA_REGISTRO_TEMPORAL);
            sqLiteDatabase.close();
        }catch (IOException e){
            throw new Error("Error: copiando base de datos");
        }

    }

    public void copyDataBase() throws IOException{
        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }


    public void copyDataBase(String inputPath) throws IOException{
//        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        InputStream myInput = new FileInputStream(inputPath);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }

    public void open() throws SQLException {
        String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    public boolean checkDataBase(){
        try{
            String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            return dbFile.exists();
        }
        if (sqLiteDatabase != null) sqLiteDatabase.close();

        return sqLiteDatabase != null ? true : false;
    }

    public UsuarioLocal getUsuarioLocal(String clave){
        UsuarioLocal usuario = null;
        String[] whereArgs = new String[]{clave};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablausuariolocal,
                    SQLConstantes.COLUMNAS_USUARIO_LOCAL,SQLConstantes.WHERE_CLAUSE_CLAVE,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                usuario = new UsuarioLocal();
                usuario.setUsuario(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_usuario)));
                usuario.setClave(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_clave)));
                usuario.setNombrelocal(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_nombrelocal)));
                usuario.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_local_sede)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return usuario;
    }

    public Nacional getNacional(String dni){
        Nacional nacional = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablanacional,
                    SQLConstantes.COLUMNAS_NACIONAL,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                nacional = new Nacional();
                nacional.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_codigo)));
                nacional.setApepat(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_apepat)));
                nacional.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_aula)));
                nacional.setDiscapacidad(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_discapacidad)));
                nacional.setLocal_aplicacion(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_aplicacion)));
                nacional.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.nacional_sede)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return nacional;
    }

    public void insertarRegistro(RegistroAsistencia registroAsistencia){
        ContentValues contentValues = registroAsistencia.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablaregistro,null,contentValues);
    }

    public void actualizarRegistro(String codigo, ContentValues valores){
        String[] whereArgs = new String[]{codigo};
        sqLiteDatabase.update(SQLConstantes.tablaregistro,valores,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs);
    }

    public RegistroAsistencia getRegistro(String dni){
        RegistroAsistencia registroAsistencia = null;
        String[] whereArgs = new String[]{dni};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
                    SQLConstantes.COLUMNAS_REGISTRO,SQLConstantes.WHERE_CLAUSE_CODIGO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                registroAsistencia = new RegistroAsistencia();
                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return registroAsistencia;
    }

    public RegistroAsistencia getRegistro(String dni, int dia, int mes, int anio){
        RegistroAsistencia registroAsistencia = null;
        String[] whereArgs = new String[]{dni,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro, SQLConstantes.COLUMNAS_REGISTRO,
                    SQLConstantes.WHERE_CLAUSE_CODIGO+ " AND " +
                            SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ANIO
                    ,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                registroAsistencia = new RegistroAsistencia();
                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return registroAsistencia;
    }

    public ArrayList<RegistroAsistencia> getAllRegistrados(String sede, int dia, int mes, int anio){
        ArrayList<RegistroAsistencia> registroAsistencias = new ArrayList<>();
        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
                    SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
                    SQLConstantes.WHERE_CLAUSE_MES + " AND " +
                    SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
            while(cursor.moveToNext()){
                RegistroAsistencia registroAsistencia = new RegistroAsistencia();
                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
                registroAsistencias.add(registroAsistencia);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return registroAsistencias;
    }

    public ArrayList<RegistroAsistencia> getAllSinEnviar(String sede, int dia, int mes, int anio){
        ArrayList<RegistroAsistencia> registroAsistencias = new ArrayList<>();
        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
                            SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
            while(cursor.moveToNext()){
                int subidoEntrada = cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada));
                int subidoSalida = cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida));
                if(subidoEntrada == 0 || subidoSalida == 0){
                    RegistroAsistencia registroAsistencia = new RegistroAsistencia();
                    registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
                    registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
                    registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
                    registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
                    registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
                    registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
                    registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
                    registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
                    registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
                    registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
                    registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
                    registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
                    registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
                    registroAsistencias.add(registroAsistencia);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return registroAsistencias;
    }

    public ArrayList<RegistroAsistencia> getAllSalidaSinEnviar(String sede, int dia, int mes, int anio){
        ArrayList<RegistroAsistencia> registroAsistencias = new ArrayList<>();
        String[] whereArgs = new String[]{sede,Integer.toString(dia),Integer.toString(mes),Integer.toString(anio)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
                    null,SQLConstantes.WHERE_CLAUSE_SEDE + " AND " +
                            SQLConstantes.WHERE_CLAUSE_DIA + " AND " +
                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ANIO, whereArgs,null,null,null);
            while(cursor.moveToNext()){
                int subidoEntrada = cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida));
                if(subidoEntrada == 0){
                    RegistroAsistencia registroAsistencia = new RegistroAsistencia();
                    registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
                    registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
                    registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
                    registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
                    registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
                    registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
                    registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
                    registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
                    registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
                    registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
                    registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
                    registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
                    registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
                    registroAsistencias.add(registroAsistencia);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return registroAsistencias;
    }

    public ArrayList<RegistroAsistencia> getAllRegistradosNube(){
        ArrayList<RegistroAsistencia> registroAsistencias = new ArrayList<>();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaregistro,
                    null,SQLConstantes.WHERE_CLAUSE_SUBIDO_ENTRADA,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                RegistroAsistencia registroAsistencia = new RegistroAsistencia();
                registroAsistencia.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_id)));
                registroAsistencia.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_codigo)));
                registroAsistencia.setNombres(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_nombres)));
                registroAsistencia.setSede(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_sede)));
                registroAsistencia.setAula(cursor.getString(cursor.getColumnIndex(SQLConstantes.registro_aula)));
                registroAsistencia.setDia(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_dia)));
                registroAsistencia.setMes(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_mes)));
                registroAsistencia.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_anio)));
                registroAsistencia.setHoraEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_entrada)));
                registroAsistencia.setMinutoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_entrada)));
                registroAsistencia.setSubidoEntrada(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_entrada)));
                registroAsistencia.setHoraSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_hora_salida)));
                registroAsistencia.setMinutoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_minuto_salida)));
                registroAsistencia.setSubidoSalida(cursor.getInt(cursor.getColumnIndex(SQLConstantes.registro_subido_salida)));
                registroAsistencias.add(registroAsistencia);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return registroAsistencias;
    }

    public void deleteAllElementosFromTabla(String nombreTabla){
        sqLiteDatabase.execSQL("delete from "+ nombreTabla);
    }

}
