package com.example.dmorales.evaluacion2018.modelo;

import android.content.ContentValues;

public class RegistroAsistencia {
    private String _id;
    private String codigo;
    private String nombres;
    private String sede;
    private String aula;
    private int dia;
    private int mes;
    private int anio;
    private int horaEntrada;
    private int minutoEntrada;
    private int horaSalida;
    private int minutoSalida;
    private int subidoEntrada;
    private int subidoSalida;


    public RegistroAsistencia(String _id, String codigo, String nombres, String sede, String aula, int dia, int mes, int anio, int horaEntrada, int minutoEntrada, int horaSalida, int minutoSalida, int subidoEntrada, int subidoSalida) {
        this._id = _id;
        this.codigo = codigo;
        this.nombres = nombres;
        this.sede = sede;
        this.aula = aula;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.horaEntrada = horaEntrada;
        this.minutoEntrada = minutoEntrada;
        this.horaSalida = horaSalida;
        this.minutoSalida = minutoSalida;
        this.subidoEntrada = subidoEntrada;
        this.subidoSalida = subidoSalida;
    }

    public RegistroAsistencia() {
        this._id = "";
        this.codigo = "";
        this.nombres = "";
        this.sede = "";
        this.aula = "";
        this.dia = 0;
        this.mes = 0;
        this.anio = 0;
        this.horaEntrada = 0;
        this.minutoEntrada = 0;
        this.horaSalida = 0;
        this.minutoSalida = 0;
        this.subidoEntrada = 0;
        this.subidoSalida = -1;
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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getMinutoEntrada() {
        return minutoEntrada;
    }

    public void setMinutoEntrada(int minutoEntrada) {
        this.minutoEntrada = minutoEntrada;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(int horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getMinutoSalida() {
        return minutoSalida;
    }

    public void setMinutoSalida(int minutoSalida) {
        this.minutoSalida = minutoSalida;
    }

    public int getSubidoEntrada() {
        return subidoEntrada;
    }

    public void setSubidoEntrada(int subidoEntrada) {
        this.subidoEntrada = subidoEntrada;
    }

    public int getSubidoSalida() {
        return subidoSalida;
    }

    public void setSubidoSalida(int subidoSalida) {
        this.subidoSalida = subidoSalida;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.registro_id,_id);
        contentValues.put(SQLConstantes.registro_codigo,codigo);
        contentValues.put(SQLConstantes.registro_nombres,nombres);
        contentValues.put(SQLConstantes.registro_sede,sede);
        contentValues.put(SQLConstantes.registro_aula,aula);
        contentValues.put(SQLConstantes.registro_dia,dia);
        contentValues.put(SQLConstantes.registro_mes,mes);
        contentValues.put(SQLConstantes.registro_anio,anio);
        contentValues.put(SQLConstantes.registro_hora_entrada, horaEntrada);
        contentValues.put(SQLConstantes.registro_minuto_entrada, minutoEntrada);
        contentValues.put(SQLConstantes.registro_subido_entrada, subidoEntrada);
        contentValues.put(SQLConstantes.registro_hora_salida, horaSalida);
        contentValues.put(SQLConstantes.registro_minuto_salida, minutoSalida);
        contentValues.put(SQLConstantes.registro_subido_salida, subidoSalida);
        return contentValues;
    }
}
