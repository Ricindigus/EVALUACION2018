package com.example.dmorales.evaluacion2018.modelo;

public class Nacional {
    private String codigo;
    private String sede;
    private String local_aplicacion;
    private String aula;
    private String apepat;
    private String discapacidad;

    public Nacional(String codigo, String sede, String local_aplicacion, String aula, String apepat, String discapacidad) {
        this.codigo = codigo;
        this.sede = sede;
        this.local_aplicacion = local_aplicacion;
        this.aula = aula;
        this.apepat = apepat;
        this.discapacidad = discapacidad;
    }

    public Nacional() {
        this.codigo = "";
        this.sede = "";
        this.local_aplicacion = "";
        this.aula = "";
        this.apepat = "";
        this.discapacidad = "";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getLocal_aplicacion() {
        return local_aplicacion;
    }

    public void setLocal_aplicacion(String local_aplicacion) {
        this.local_aplicacion = local_aplicacion;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }
}
