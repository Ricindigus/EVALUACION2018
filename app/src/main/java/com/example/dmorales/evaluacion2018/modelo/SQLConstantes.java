package com.example.dmorales.evaluacion2018.modelo;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/com.example.dmorales.evaluacion2018/databases/";
    public static String DB_NAME = "mydatabase.sqlite";

    public static String tablanacional = "nacional";
    public static String tablausuariolocal = "usuario_local";
    public static String tablaregistro = "fecha_registro";
//    public static String tablafecharegistrotemporal = "fecha_registro_temporal";


    public static String nacional_codigo = "codigo";
    public static String nacional_sede = "sede";
    public static String nacional_aplicacion = "local_aplicacion";
    public static String nacional_aula = "aula";
    public static String nacional_apepat = "apepat";
    public static String nacional_discapacidad = "discapacidad";

    public static String usuario_local_usuario = "usuario";
    public static String usuario_local_clave = "clave";
    public static String usuario_local_nombrelocal = "nombreLocal";
    public static String usuario_local_sede = "sede";

    public static String registro_id = "_id";
    public static String registro_codigo = "codigo";
    public static String registro_nombres = "nombres";
    public static String registro_sede = "sede";
    public static String registro_aula = "aula";
    public static String registro_dia = "dia";
    public static String registro_mes = "mes";
    public static String registro_anio = "anio";
    public static String registro_hora_entrada = "hora_entrada";
    public static String registro_minuto_entrada = "minuto_entrada";
    public static String registro_hora_salida = "hora_salida";
    public static String registro_minuto_salida = "minuto_salida";
    public static String registro_subido_entrada = "subido_entrada";
    public static String registro_subido_salida = "subido_salida";


    public static final String SQL_CREATE_TABLA_REGISTRO =
            "CREATE TABLE " + tablaregistro + "(" +
                    registro_id + " TEXT PRIMARY KEY," +
                    registro_codigo + " TEXT," +
                    registro_nombres + " TEXT," +
                    registro_sede + " TEXT," +
                    registro_aula + " TEXT," +
                    registro_dia + " INTEGER," +
                    registro_mes + " INTEGER," +
                    registro_anio + " INTEGER," +
                    registro_hora_entrada + " INTEGER," +
                    registro_minuto_entrada + " INTEGER," +
                    registro_hora_salida + " INTEGER," +
                    registro_minuto_salida + " INTEGER," +
                    registro_subido_entrada + " INTEGER," +
                    registro_subido_salida + " INTEGER" + ");"
            ;

//    public static final String SQL_CREATE_TABLA_FECHA_REGISTRO_TEMPORAL =
//            "CREATE TABLE " + tablafecharegistrotemporal + "(" +
//                    registro_id + " TEXT PRIMARY KEY," +
//                    registro_codigo + " TEXT," +
//                    registro_nombres + " TEXT," +
//                    registro_sede + " TEXT," +
//                    registro_aula + " TEXT," +
//                    registro_dia + " TEXT," +
//                    registro_mes + " TEXT," +
//                    registro_anio + " TEXT," +
//                    fecha_de_registro_hora + " TEXT," +
//                    fecha_de_registro_minuto + " TEXT" + ");"
//            ;


    public static final String WHERE_CLAUSE_CLAVE = "clave=?";
    public static final String WHERE_CLAUSE_CODIGO = "codigo=?";
    public static final String WHERE_CLAUSE_SEDE = "sede=?";
    public static final String WHERE_CLAUSE_DIA = "dia=?";
    public static final String WHERE_CLAUSE_MES = "mes=?";
    public static final String WHERE_CLAUSE_ANIO = "anio=?";


    public static final String WHERE_CLAUSE_SUBIDO_ENTRADA = "subido_entrada=?";
    public static final String WHERE_CLAUSE_SUBIDO_SALIDA = "subido_salida=?";



    public static final String[] COLUMNAS_NACIONAL = {
            nacional_codigo,nacional_apepat,nacional_aplicacion,
            nacional_aula, nacional_discapacidad, nacional_sede
    };

    public static final String[] COLUMNAS_USUARIO_LOCAL = {
            usuario_local_clave, usuario_local_nombrelocal,
            usuario_local_sede, usuario_local_usuario
    };

    public static final String[] COLUMNAS_REGISTRO = {
            registro_id, registro_codigo, registro_nombres,
            registro_sede, registro_aula, registro_anio,
            registro_dia, registro_hora_entrada, registro_mes,
            registro_minuto_entrada, registro_subido_entrada,
            registro_hora_salida, registro_minuto_salida,
            registro_subido_salida
    };

}
