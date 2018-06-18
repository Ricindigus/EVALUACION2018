package com.example.dmorales.evaluacion2018.modelo;

public class UsuarioLocal {
    private String usuario;
    private String clave;
    private String nombrelocal;
    private String sede;

    public UsuarioLocal(String usuario, String clave, String nombrelocal, String sede) {
        this.usuario = usuario;
        this.clave = clave;
        this.nombrelocal = nombrelocal;
        this.sede = sede;
    }

    public UsuarioLocal() {
        this.usuario = "";
        this.clave = "";
        this.nombrelocal = "";
        this.sede = "";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombrelocal() {
        return nombrelocal;
    }

    public void setNombrelocal(String nombrelocal) {
        this.nombrelocal = nombrelocal;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
