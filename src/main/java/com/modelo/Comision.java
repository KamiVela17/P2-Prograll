/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modelo;

/**
 *
 * @author KamiVela
 */
public class Comision {

    int id;
    String usuario;
    String persona;
    double enero;
    double febrero;
    double marzo;
    double promedio;
    double total;

    public Comision(String usuario, String Persona, double enero, double febrero, double marzo, double promedio, double total) {
        this.id = id;
        this.usuario = usuario;
        this.persona = Persona;
        this.enero = enero;
        this.febrero = febrero;
        this.marzo = marzo;
        this.promedio = promedio;
        this.total = total;
    }

    public Comision() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String Persona) {
        this.persona = Persona;
    }

    public double getEnero() {
        return enero;
    }

    public void setEnero(double enero) {
        this.enero = enero;
    }

    public double getFebrero() {
        return febrero;
    }

    public void setFebrero(double febrero) {
        this.febrero = febrero;
    }

    public double getMarzo() {
        return marzo;
    }

    public void setMarzo(double marzo) {
        this.marzo = marzo;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
