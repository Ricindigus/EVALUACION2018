package com.example.dmorales.evaluacion2018.modelo;

import android.content.ContentValues;

public class Registrado {
    private String _id;
    private String codigo;
    private String nombres;
    private String sede;
    private String aula;
    private String dia;
    private String mes;
    private String anio;
    private String hora;
    private String minuto;
    private int subido;


    public Registrado(String _id, String codigo, String nombres, String sede, String aula, String dia, String mes, String anio, String hora, String minuto, int subido) {
        this._id = _id;
        this.codigo = codigo;
        this.nombres = nombres;
        this.sede = sede;
        this.aula = aula;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.subido = subido;
    }

    public Registrado() {
        this._id = "";
        this.codigo = "";
        this.nombres = "";
        this.sede = "";
        this.aula = "";
        this.dia = "";
        this.mes = "";
        this.anio = "";
        this.hora = "";
        this.minuto = "";
        this.subido = 0;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.fecha_de_registro_id,_id);
        contentValues.put(SQLConstantes.fecha_de_registro_codigo,codigo);
        contentValues.put(SQLConstantes.fecha_de_registro_nombres,nombres);
        contentValues.put(SQLConstantes.fecha_de_registro_sede,sede);
        contentValues.put(SQLConstantes.fecha_de_registro_aula,aula);
        contentValues.put(SQLConstantes.fecha_de_registro_dia,dia);
        contentValues.put(SQLConstantes.fecha_de_registro_mes,mes);
        contentValues.put(SQLConstantes.fecha_de_registro_anio,anio);
        contentValues.put(SQLConstantes.fecha_de_registro_hora,hora);
        contentValues.put(SQLConstantes.fecha_de_registro_minuto,minuto);
        contentValues.put(SQLConstantes.fecha_de_registro_subido,subido);
        return contentValues;
    }
}
